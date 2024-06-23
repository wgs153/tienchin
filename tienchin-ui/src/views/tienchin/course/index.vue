<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="活动名称" prop="name">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入活动名称"
            clearable
            style="width: 180px"
        />
      </el-form-item>

      <el-form-item label="渠道名称" prop="channelId">
        <el-select v-model="queryParams.channelId" placeholder="请选择渠道" clearable style="width: 180px">
          <el-option
              v-for="dict in channelList"
              :key="dict.channelId"
              :label="dict.channelName"
              :value="dict.channelId"
          />
        </el-select>
      </el-form-item>


      <el-form-item label="活动状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择活动状态" clearable style="width: 180px">
          <el-option
              v-for="dict in activity_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="活动类型" prop="type" clearable>
        <el-select
            v-model="queryParams.type"
            placeholder="请选择活动类型"
            clearable
            style="width: 180px"
        >
          <el-option
              v-for="dict in activity_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button type="primary" icon="Search" @click="handleQuery">搜索</el-button>
        <el-button icon="Refresh" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
            type="primary"
            plain
            icon="Plus"
            @click="handleAdd"
            v-hasPermi="['tienchin:course:create']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="Edit"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['tienchin:activity:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="Delete"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['tienchin:activity:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['tienchin:activity:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center"/>
      <el-table-column label="课程编号" align="center" prop="courseId" width="80"/>
      <el-table-column label="课程类型" align="type" width="80">
        <template #default="scope">
          <dict-tag :options="course_type" :value="scope.row.type"/>
        </template>
      </el-table-column>

      <el-table-column label="课程名称" align="center" prop="name" width="80"/>
      <el-table-column label="课程价格" align="center" prop="price" width="80"/>
      <el-table-column label="课程简介" :show-overflow-tooltip="true" align="center" prop="info" width="200"/>
      <el-table-column label="适用人群" align="center" width="80">
        <template #default="scope">
          <dict-tag :options="course_apply_to" :value="scope.row.applyTo"/>
        </template>
      </el-table-column>
      <el-table-column label="备注信息" :show-overflow-tooltip="true" align="center" prop="remark" width="200"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>


      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
              type="text"
              icon="Edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['tienchin:activity:edit']"
          >修改
          </el-button>
          <el-button
              type="text"
              icon="Delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['tienchin:activity:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
        v-show="total > 0"
        :total="total"
        v-model:page="queryParams.pageNum"
        v-model:limit="queryParams.pageSize"
        @pagination="getList"
    />

    <!-- 添加或修改岗位对话框 -->
    <el-dialog :title="title" v-model="open" width="550px" append-to-body>
      <el-form :model="form" :rules="rules" ref="courseRef">
        <el-row>
          <el-col :span="14">
            <el-form-item label="课程名称" prop="name">
              <el-input v-model="form.name"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="课程价格" prop="price">
              <el-input v-model="form.price" placeholder="请输入课程价格"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="适用人群" prop="applyTo">
              <el-select v-model="form.applyTo" placeholder="请选择适用人群">
                <el-option
                    v-for="dice in course_apply_to"
                    :key="dice.value"
                    :value="dice.value"
                    :label="dice.label">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="课程类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择课程类型">
                <el-option
                    v-for="dict in course_type"
                    :label="dict.label"
                    :value="dict.value"
                    :key="dict.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="课程简介" prop="info">
          <el-input type="textarea" v-model="form.info"></el-input>
        </el-form-item>
        <el-form-item label="备注信息" prop="remark">
          <el-input type="textarea" v-model="form.remark"></el-input>
        </el-form-item>
      </el-form>

      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="reset()">重置</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Post">
import {listActivity, listChannel, addActivity, getPost, updateActivity, delActivity} from "@/api/tienchin/activity";
import {listCourse, addCourse} from "@/api/tienchin/course";
import {parseTime} from "../../../utils/ruoyi";

const {proxy} = getCurrentInstance();
// const {activity_type, activity_status} = proxy.useDict("activity_type", "activity_status");
const {course_apply_to, course_type} = proxy.useDict("course_apply_to", "course_type");

const courseList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const channelList = ref([]);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    name: undefined,
    channelId: undefined,
    status: undefined,
    type: undefined
  },
  rules: {
    name: [{required: true, message: "课程名称不能为空", trigger: "blur"}],
    type: [{required: true, message: "类型不能为空", trigger: "blur"}],
    price: [
      {required: true, message: "价格不能为空", trigger: "blur"},
      {
        validator: (rule, value, callback) => {
          if (value <= 0) {
            callback(new Error("价格太低"));
          } else {
            callback();
          }
        },
        trigger: "blur"
      }
      ],
    info: [{required: true, message: "简介不能为空", trigger: "blur"}],
    applyTo: [{required: true, message: "适用人群不能为空", trigger: "blur"}]
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询课程列表 */
function getList() {
  loading.value = true;
  listCourse(queryParams.value).then(response => {
    courseList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}

/** 表单重置 */
function reset() {
  form.value = {
    courseId: undefined,
    type: undefined,
    name: undefined,
    price: undefined,
    info: undefined,
    applyTo: undefined,
    remark: undefined
  };
  proxy.resetForm("courseRef");
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.activityId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加课程";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const activityId = row.activityId || ids.value;
  getPost(activityId).then(response => {
    form.value = response.data;
    form.value.activityTime = new Array();
    form.value.activityTime.push(form.value.beginTime);
    form.value.activityTime.push(form.value.endTime);
    // 加载渠道列表
    handleChannelList();
    open.value = true;
    title.value = "修改活动";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["courseRef"].validate(valid => {
    if (valid) {
      if (form.value.activityId != undefined) {
        form.value.beginTime = form.value.activityTime[0];
        form.value.endTime = form.value.activityTime[1];
        delete form.value.activityTime;
        delete form.value.createTime;
        delete form.value.updateTime;

        updateActivity(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addCourse(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });
      }
    }
  });
}

/** 删除按钮操作 */
function handleDelete(row) {
  const activityIds = row.activityId || ids.value;
  proxy.$modal.confirm('是否确认删除活动编号为"' + activityIds + '"的数据项？').then(function () {
    return delActivity(activityIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download("tienchin/activity/export", {
    ...queryParams.value
  }, `activity_${new Date().getTime()}.xlsx`);
}

/** 查询渠道列表 */
function handleChannelList() {
  listChannel().then(response => {
    channelList.value = response.data;

  });
}

getList();
handleChannelList();
</script>
