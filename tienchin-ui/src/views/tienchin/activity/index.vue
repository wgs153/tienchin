<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="岗位编码" prop="postCode">
        <el-input
            v-model="queryParams.postCode"
            placeholder="请输入岗位编码"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="岗位名称" prop="postName">
        <el-input
            v-model="queryParams.postName"
            placeholder="请输入岗位名称"
            clearable
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="岗位状态" clearable>
          <el-option
              v-for="dict in sys_normal_disable"
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
            v-hasPermi="['tienchin:activity:create']"
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

    <el-table v-loading="loading" :data="activityList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center"/>
      <el-table-column label="活动编号" align="center" prop="activityId" width="80"/>
      <el-table-column label="活动名称" align="center" prop="name" width="80"/>
      <el-table-column label="渠道名称" align="center" prop="channelName" width="80"/>
      <el-table-column label="活动简介" :show-overflow-tooltip="true" align="center" prop="info" width="200"/>
      <el-table-column label="活动类型" align="center" width="80">
        <template #default="scope">
          <dict-tag :options="activity_type" :value="scope.row.type"/>
        </template>
      </el-table-column>
      <el-table-column label="活动明细" align="center" width="120">
        <template #default="scope">
          <div v-if="scope.row.type==1">
            折扣券/{{ scope.row.discount }}折
          </div>
          <div v-if="scope.row.type==2">
            代金券/{{ scope.row.voucher }}元
          </div>
        </template>
      </el-table-column>
      <el-table-column label="活动状态" align="center" width="80">
        <template #default="scope">
          <dict-tag :options="activity_status" :value="scope.row.status"/>
        </template>
      </el-table-column>

      <el-table-column label="活动开始时间" align="center" prop="beginTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.beginTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动结束时间" align="center" prop="endTime" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
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
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="activityRef" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="渠道来源" prop="channelId">
              <el-select v-model="form.channelId" placeholder="请选择渠道来源">
                <el-option
                    v-for="channel in channelList"
                    :key="channel.channelId"
                    :label="channel.channelName"
                    :value="channel.channelId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动名称" prop="name">
              <el-input v-model="form.name" placeholder="请输入活动名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="活动日期" style="width: 100%" prop="activityTime">
              <el-date-picker
                  style="width:100%"
                  v-model="form.activityTime"
                  value-format="YYYY-MM-DD HH:mm:ss"
                  type="datetimerange"
                  range-separator="-"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label="活动简介" prop="info" style="width: 100%">
              <el-input v-model="form.info" size="mini" type="textarea" :autosize="{ minRows: 2, maxRows: 3}"
                        placeholder="请输入活动简介"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="活动类型" prop="type">
              <el-select v-model="form.type" placeholder="请选择活动类型">
                <el-option
                    v-for="dict in activity_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="parseInt(dict.value)">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="折扣券" prop="discount" v-if="form.type==1">
              <el-input-number style="width: 100%" v-model="form.discount" :precision="2" :step="0.5" :min="0" :max="9" placeholder="请输入折扣值"></el-input-number>
            </el-form-item>
            <el-form-item label="代金券" prop="voucher" v-if="form.type==2">
              <el-input-number style="width: 100%" v-model="form.voucher" :precision="0" :step="100" :min="0" placeholder="请输入代金券面值"></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>
  </div>
</template>

<script setup name="Post">
import {listPost, addPost, delPost, updatePost} from "@/api/system/post";
import {listActivity, listChannel,addActivity,getPost,updateActivity} from "@/api/tienchin/activity";

const {proxy} = getCurrentInstance();
const {sys_normal_disable, activity_type, activity_status} = proxy.useDict("activity_type", "activity_status");

const activityList = ref([]);
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
    postCode: undefined,
    postName: undefined,
    status: undefined
  },
  rules: {
    channelId: [{required: true, message: "渠道来源不能为空", trigger: "blur"}],
    name: [{required: true, message: "活动名称不能为空", trigger: "blur"}],
    activityTime: [{required: true, message: "活动日期不能为空", trigger: "blur"}],
    info: [{required: true, message: "活动简介不能为空", trigger: "blur"}],
    type: [{required: true, message: "活动类型不能为空", trigger: "blur"}],
    discount: [{required: true, message: "折扣券不能为空", trigger: "blur"}],
    voucher : [{required: true, message: "代金券不能为空", trigger: "blur"}]
  }
});

const {queryParams, form, rules} = toRefs(data);

/** 查询活动列表 */
function getList() {
  loading.value = true;
  listActivity(queryParams.value).then(response => {
    activityList.value = response.rows;
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
    channelId: undefined,
    name: undefined,
    activityTime: undefined,
    info: undefined,
    type: undefined,
    discount: undefined,
    voucher: undefined,
    remark: undefined
  };
  proxy.resetForm("activityRef");
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
  // 加载渠道列表
  handleChannelList();
  open.value = true;
  title.value = "添加活动";
}

/** 修改按钮操作 */
function handleUpdate(row) {
  reset();
  const activityId = row.activityId || ids.value;
  getPost(activityId).then(response => {
    form.value = response.data;
    form.value.activityTime=new Array();
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
  proxy.$refs["activityRef"].validate(valid => {
    if (valid) {
      if (form.value.activityId != undefined) {
        form.value.beginTime=form.value.activityTime[0];
        form.value.endTime=form.value.activityTime[1];
        delete form.value.activityTime;
        delete form.value.createTime;
        delete form.value.updateTime;

        updateActivity(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        form.value.beginTime=form.value.activityTime[0];
        form.value.endTime=form.value.activityTime[1];
        delete form.value.activityTime;
        addActivity(form.value).then(response => {
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
  const postIds = row.postId || ids.value;
  proxy.$modal.confirm('是否确认删除岗位编号为"' + postIds + '"的数据项？').then(function () {
    return delPost(postIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download("system/post/export", {
    ...queryParams.value
  }, `post_${new Date().getTime()}.xlsx`);
}

/** 查询渠道列表 */
function handleChannelList() {
  listChannel().then(response => {
    channelList.value = response.data;
  });
}

getList();
</script>
