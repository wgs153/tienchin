<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryRef" v-show="showSearch" :inline="true">
      <el-form-item label="渠道名称" prop="channelName">
        <el-input
            v-model="queryParams.channelName"
            placeholder="请输入渠道名称"
            clearable
            style="width: 240px"
            @keyup.enter="handleQuery"
        />
      </el-form-item>
      <el-form-item label="渠道状态" prop="status">
        <el-select
            v-model="queryParams.status"
            placeholder="请选择渠道状态"
            clearable
            style="width: 240px"
        >
          <el-option
              v-for="dict in channel_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="渠道类型" prop="type">
        <el-select
            v-model="queryParams.type"
            placeholder="请选择渠道类型"
            clearable
            style="width: 240px"
        >
          <el-option
              v-for="dict in channel_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="更新时间" style="width: 308px">
        <el-date-picker
            v-model="dateRange"
            value-format="YYYY-MM-DD"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
        ></el-date-picker>
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
            v-hasPermi="['tienchin:channel:create']"
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
            v-hasPermi="['tienchin:channel:edit']"
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
            v-hasPermi="['tienchin:channel:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="Download"
            @click="handleExport"
            v-hasPermi="['tienchin:channel:export']"
        >导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="info"
            plain
            icon="Upload"
            @click="handleImport"
            v-hasPermi="['tienchin:channel:import']"
        >导入
        </el-button>
      </el-col>

      <right-toolbar v-model:showSearch="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <!-- 表格数据 -->
    <el-table v-loading="loading" :data="channelList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="渠道编号" prop="channelId" width="120"/>
      <el-table-column label="渠道名称" prop="channelName" :show-overflow-tooltip="true" width="150"/>
      <el-table-column label="渠道状态" :show-overflow-tooltip="true" width="100">
        <template #default="scope">
          <template v-for="(status,index) in channel_status">
            <el-tag :key="index" v-if="scope.row.status==status.value" :type="status.elTagType">{{ status.label }}
            </el-tag>
          </template>
        </template>
      </el-table-column>

      <el-table-column label="渠道类型" :show-overflow-tooltip="true" width="100">
        <template #default="scope">
          <template v-for="(type,index) in channel_type">
            <el-tag :key="index" v-if="type.value==scope.row.type" :type="type.elTagType">{{ type.label }}</el-tag>
          </template>
        </template>
      </el-table-column>

      <el-table-column label="创建人" prop="createBy" :show-overflow-tooltip="true" width="80"/>
      <el-table-column label="更新人" prop="updateBy" :show-overflow-tooltip="true" width="80"/>
      <el-table-column label="更新时间" :show-overflow-tooltip="true" width="250">
        <template #default="scope">
          <sapn>{{ parseTime(scope.row.updateTime) }}</sapn>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark" :show-overflow-tooltip="true" width="250"/>

      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-tooltip content="修改" placement="top">
            <el-button
                type="text"
                icon="Edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['tienchin:channel:edit']"
            ></el-button>
          </el-tooltip>
          <el-tooltip content="删除" placement="top">
            <el-button
                type="text"
                icon="Delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['tienchin:channel:remove']"
            ></el-button>
          </el-tooltip>

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

    <!-- 添加或修改渠道配置对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="channelRef" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="渠道名称" prop="channelName">
          <el-input v-model="form.channelName" placeholder="请输入渠道名称"/>
        </el-form-item>
        <el-form-item label="渠道状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
                v-for="dict in channel_status"
                :key="dict.value"
                :label="parseInt(dict.value)"
            >{{ dict.label }}
            </el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item label="渠道类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择">
            <el-option
                v-for="dict in channel_type"
                :key="dict.type"
                :label="dict.label"
                :value="parseInt(dict.value)">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </template>
    </el-dialog>

    <!-- 分配角色数据权限对话框 -->
    <el-dialog :title="title" v-model="openDataScope" width="500px" append-to-body>
      <el-form :model="form" label-width="80px">
        <el-form-item label="角色名称">
          <el-input v-model="form.roleName" :disabled="true"/>
        </el-form-item>
        <el-form-item label="权限字符">
          <el-input v-model="form.roleKey" :disabled="true"/>
        </el-form-item>
        <el-form-item label="权限范围">
          <el-select v-model="form.dataScope" @change="dataScopeSelectChange">
            <el-option
                v-for="item in dataScopeOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="数据权限" v-show="form.dataScope == 2">
          <el-checkbox v-model="deptExpand" @change="handleCheckedTreeExpand($event, 'dept')">展开/折叠</el-checkbox>
          <el-checkbox v-model="deptNodeAll" @change="handleCheckedTreeNodeAll($event, 'dept')">全选/全不选
          </el-checkbox>
          <el-checkbox v-model="form.deptCheckStrictly" @change="handleCheckedTreeConnect($event, 'dept')">父子联动
          </el-checkbox>
          <el-tree
              class="tree-border"
              :data="deptOptions"
              show-checkbox
              default-expand-all
              ref="deptRef"
              node-key="id"
              :check-strictly="!form.deptCheckStrictly"
              empty-text="加载中，请稍候"
              :props="{ label: 'label', children: 'children' }"
          ></el-tree>
        </el-form-item>
      </el-form>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitDataScope">确 定</el-button>
          <el-button @click="cancelDataScope">取 消</el-button>
        </div>
      </template>
    </el-dialog>
    <!-- 渠道导入对话框 -->
    <el-dialog :title="upload.title" v-model="upload.open" width="400px" append-to-body>
      <el-upload
          ref="uploadRef"
          :limit="1"
          accept=".xlsx, .xls"
          :headers="upload.headers"
          :action="upload.url + '?updateSupport=' + upload.updateSupport"
          :disabled="upload.isUploading"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess"
          :auto-upload="false"
          drag
      >
        <el-icon class="el-icon--upload">
          <upload-filled/>
        </el-icon>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <template #tip>
          <div class="el-upload__tip text-center">
            <div class="el-upload__tip">
              <el-checkbox v-model="upload.updateSupport"/>
              是否更新已经存在的渠道数据
            </div>
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                     @click="importTemplate">下载模板
            </el-link>
          </div>
        </template>
      </el-upload>
      <template #footer>
        <div class="dialog-footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </template>
    </el-dialog>

  </div>
</template>

<script setup name="Channel">
import {getToken} from "@/utils/auth";
import {listChannel, addChannel, getChannel, updateChannel, delChannel} from "@/api/tienchin/channel";

const {proxy} = getCurrentInstance();
const {
  channel_type,
  channel_status
} = proxy.useDict("channel_type", "channel_status");

const channelList = ref([]);
const open = ref(false);
const loading = ref(true);
const showSearch = ref(true);
const ids = ref([]);
const single = ref(true);
const multiple = ref(true);
const total = ref(0);
const title = ref("");
const dateRange = ref([]);

/*** 渠道导入参数 */
const upload = reactive({
  // 是否显示弹出层（渠道导入）
  open: false,
  // 弹出层标题（渠道导入）
  title: "",
  // 是否禁用上传
  isUploading: false,
  // 是否更新已经存在的渠道数据
  updateSupport: 0,
  // 设置上传的请求头部
  headers: {Authorization: "Bearer " + getToken()},
  // 上传的地址
  url: import.meta.env.VITE_APP_BASE_API + "/tienchin/channel/importData"
});

/** 数据范围选项*/
const dataScopeOptions = ref([
  {value: "1", label: "全部数据权限"},
  {value: "2", label: "自定数据权限"},
  {value: "3", label: "本部门数据权限"},
  {value: "4", label: "本部门及以下数据权限"},
  {value: "5", label: "仅本人数据权限"}
]);

const data = reactive({
  form: {},
  queryParams: {
    pageNum: 1,
    pageSize: 10,
    channelName: undefined,
    type: undefined,
    status: undefined
  },
  rules: {
    channelName: [{required: true, message: "渠道名称不能为空", trigger: "blur"}],
    status: [{required: true, message: "渠道状态不能为空", trigger: "blur"}],
    type: [{required: true, message: "渠道类型不能为空", trigger: "blur"}]
  },
});

const {queryParams, form, rules} = toRefs(data);


/** 查询渠道列表 */
function getList() {
  loading.value = true;
  listChannel(proxy.addDateRange(queryParams.value, dateRange.value)).then(response => {
    channelList.value = response.rows;
    total.value = response.total;
    loading.value = false;
  });
}

/** 搜索按钮操作 */
function handleQuery() {
  queryParams.value.pageNum = 1;
  getList();
}

/** 重置按钮操作 */
function resetQuery() {
  dateRange.value = [];
  proxy.resetForm("queryRef");
  handleQuery();
}

/** 删除按钮操作 */
function handleDelete(row) {
  const roleIds = row.channelId || ids.value;
  proxy.$modal.confirm('是否确认删除渠道编号为"' + roleIds + '"的数据项?').then(function () {
    return delChannel(roleIds);
  }).then(() => {
    getList();
    proxy.$modal.msgSuccess("删除成功");
  }).catch(() => {
  });
}

/** 导出按钮操作 */
function handleExport() {
  proxy.download("tienchin/channel/export", {
    ...queryParams.value,
  }, `channel_${new Date().getTime()}.xlsx`);
}

/** 导入按钮操作 */
function handleImport() {
  upload.title = "渠道导入";
  upload.open = true;
}

/** 下载模板操作 */
function importTemplate() {
  proxy.download("tienchin/channel/importTemplate", {}, `channel_template_${new Date().getTime()}.xlsx`);
}

/**文件上传中处理 */
const handleFileUploadProgress = (event, file, fileList) => {
  upload.isUploading = true;
};
/** 文件上传成功处理 */
const handleFileSuccess = (response, file, fileList) => {
  upload.open = false;
  upload.isUploading = false;
  proxy.$refs["uploadRef"].handleRemove(file);
  proxy.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
  getList();
};


/** 多选框选中数据 */
function handleSelectionChange(selection) {
  ids.value = selection.map(item => item.channelId);
  single.value = selection.length != 1;
  multiple.value = !selection.length;
}

/** 重置新增的表单以及其他数据  */
function reset() {

  form.value = {
    channelName: undefined,
    status: undefined,
    type: undefined,
    remark: undefined
  };
  proxy.resetForm("channelRef");
}

/** 添加渠道 */
function handleAdd() {
  reset();
  open.value = true;
  title.value = "添加渠道";
}

/** 修改渠道 */
function handleUpdate(row) {
  //点击修改按钮，到此方法，重置表单数据，然后根据id查询渠道数据赋值给表单
  reset();
  const channelId = row.channelId || ids.value;
  getChannel(channelId).then(response => {
    open.value = true;
    form.value = response.data;
    title.value = "修改渠道";
  });
}


/** 提交按钮 */
function submitForm() {
  proxy.$refs["channelRef"].validate(valid => {
    if (valid) {
      //删除无用数据
      delete form.value.updateTime;
      delete form.value.createTime;
      if (form.value.channelId != undefined) {
        updateChannel(form.value).then(response => {
          proxy.$modal.msgSuccess("修改成功");
          open.value = false;
          getList();
        });
      } else {
        addChannel(form.value).then(response => {
          proxy.$modal.msgSuccess("新增成功");
          open.value = false;
          getList();
        });

      }
    }
  });
}

/** 提交上传文件 */
function submitFileForm() {
  proxy.$refs["uploadRef"].submit();
};

/** 取消按钮 */
function cancel() {
  open.value = false;
  reset();
}

getList();
</script>
