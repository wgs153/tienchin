<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="线索名称" prop="name">
        <el-input
            v-model="queryParams.name"
            placeholder="请输入线索名称"
            clearable
            style="width: 180px"
        />
      </el-form-item>

      <el-form-item label="线索类型" prop="channelId">
        <el-select v-model="queryParams.type" placeholder="请选择线索类型" clearable style="width: 150px">
          <el-option
              v-for="dict in course_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>

      <el-form-item label="适用人群" prop="applyTo">
        <el-select v-model="queryParams.applyTo" placeholder="请选择适用人群" clearable style="width: 150px">
          <el-option
              v-for="dict in course_apply_to"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="最低价格" prop="minPrice">
        <el-input-number placeholder="最低价格" v-model="queryParams.minPrice" :precision="1" :step="100" :mix="100"></el-input-number>
      </el-form-item>
      <el-form-item label="最高价格" prop="maxPrice" clearable>
        <el-input-number placeholder="最高价格" v-model="queryParams.maxPrice" :precision="1" :step="100" :max="10000"></el-input-number>
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
            v-hasPermi="['tienchin:clue:create']"
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
            v-hasPermi="['tienchin:clue:edit']"
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
            v-hasPermi="['tienchin:clue:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['tienchin:clue:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="courseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center"/>
      <el-table-column label="线索编号" align="center" prop="courseId" width="80"/>
      <el-table-column label="线索类型" align="type" width="80">
        <template #default="scope">
          <dict-tag :options="course_type" :value="scope.row.type"/>
        </template>
      </el-table-column>

      <el-table-column label="线索名称" align="center" prop="name" width="80"/>
      <el-table-column label="线索价格" align="center" prop="price" width="80"/>
      <el-table-column label="线索简介" :show-overflow-tooltip="true" align="center" prop="info" width="200"/>
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
              v-hasPermi="['tienchin:clue:edit']"
          >修改
          </el-button>
          <el-button
              type="text"
              icon="Delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['tienchin:clue:remove']"
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
    <el-dialog :title="title" v-model="open" width="1000px" append-to-body>
      <el-form :model="form" :rules="rules" ref="clueRef">
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入客户姓名"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="手机号码" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入手机号"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="渠道来源" prop="channelId">
              <el-select v-model="form.channelId" @change="channelChange" placeholder="请选择渠道来源" style="width: 100%">
                <el-option
                    v-for="channel in channelList"
                    :key="channel.channelId"
                    :value="channel.channelId"
                    :label="channel.channelName">

                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动信息" prop="activityId">
              <el-select v-model="form.activityId" placeholder="请选择活动信息" style="width: 100%">
                <el-option
                    v-for="activity in activityList"
                    :label="activity.name"
                    :value="activity.activityId"
                    :key="activity.activityId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="客户性别" prop="gender">
              <el-radio-group v-model="form.gender">
                <el-radio v-for="dict in sys_user_sex" :key="dict.value" :label="parseInt(dict.value)">
                  {{ dict.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="客户年龄" prop="age">
              <el-input-number v-model="form.age" :precision="0" :step="1" :min="8" :size="large"
                               placeholder="年龄"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="微信号码" prop="weixin">
              <el-input  v-model="form.weixin" placeholder="请输入微信号码"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="QQ号码" prop="qq">
              <el-input  v-model="form.qq" placeholder="请输入QQ号码"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

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
import {listCourse, addCourse,getPost,updateCourse,delCourse} from "@/api/tienchin/course";
import {addClue, listActivityByChannelId, listChannels} from "@/api/tienchin/clue";
import {parseTime} from "../../../utils/ruoyi";

const {proxy} = getCurrentInstance();
const {course_apply_to, course_type} = proxy.useDict("course_apply_to", "course_type");
const {sys_user_sex} = proxy.useDict("sys_user_sex");

const courseList = ref([]);
const channelList = ref([]);
const activityList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    courseId:undefined,
    name: undefined,
    type: undefined,
    applyTo: undefined,
    minPrice: undefined,
    maxPrice: undefined
  },
  rules: {
    name: [{required: true, message: "客户姓名不能为空", trigger: "blur"}],
    phone: [{required: true, message: "手机号码不能为空", trigger: "blur"}],
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询线索列表 */
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
    name: undefined,
    phone: undefined,
    channelId: undefined,
    activityId: undefined,
    gender: undefined,
    age: undefined,
    weixin: undefined,
    qq: undefined
  };
  proxy.resetForm("clueRef");
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
  ids.value = selection.map(item => item.courseId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 新增按钮操作 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加线索";
  handleChannels();
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const courseId = row.courseId || ids.value;
  getPost(courseId).then(response => {
    form.value = response.data;
    open.value = true;
    title.value = "修改线索";
  });
}

/** 提交按钮 */
function submitForm() {
  proxy.$refs["clueRef"].validate(valid => {
    if (valid) {
      if (form.value.courseId != undefined) {
        updateCourse(form.value).then(response => {
          response.code !== 200?proxy.$modal.msgError("修改失败"):proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addClue(form.value).then(response => {
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
  const courseIds = row.courseId || ids.value;
  proxy.$modal.confirm('是否确认删除线索Id为"' + courseIds + '"的数据项？').then(function () {
    return delCourse(courseIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download("tienchin/course/export", {
    ...queryParams.value
  }, `course_${new Date().getTime()}.xlsx`);
}
/** 加载渠道列表 */
function handleChannels() {
  listChannels().then(response =>{
    channelList.value = response.data;
  });
}
/** 根据渠道id查询活动列表 */
function channelChange(channelId){
  form.value.activityId=undefined;
  listActivityByChannelId(channelId).then(response =>{
    activityList.value=response.data;
  })
}


getList();
</script>
