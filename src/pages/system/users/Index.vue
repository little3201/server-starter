<script setup lang="ts">
import { ref, reactive, onMounted, watch } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import { dayjs } from 'element-plus'
import draggable from 'vuedraggable'
import Dialog from 'components/Dialog.vue'
import { retrieveGroupTree } from 'src/api/groups'
import { retrieveUsers, fetchUser, createUser, modifyUser, removeUser } from 'src/api/users'
import { retrieveRoles } from 'src/api/roles'
import type { Pagination, TreeNode, User, Role } from 'src/models'

const loading = ref<boolean>(false)
const datas = ref<Array<User>>([])
const total = ref<number>(0)

const pagination = reactive<Pagination>({
  page: 1,
  size: 10,
  sortBy: 'id',
  descending: true
})

const checkAll = ref<boolean>(true)
const isIndeterminate = ref<boolean>(false)
const checkedColumns = ref<Array<string>>(['name', 'enabled', 'description'])
const columns = ref<Array<string>>(['name', 'enabled', 'description'])

const saveLoading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)

const filters = ref({
  groupId: null,
  username: null
})

const formRef = ref<FormInstance>()
const initialValues: User = {
  username: '',
  email: '',
  roleId: null,
  accountNonLocked: true,
  groupId: null
}
const form = ref<User>({ ...initialValues })

const rules = reactive<FormRules<typeof form>>({
  username: [
    { required: true, trigger: 'blur' }
  ],
  fullName: [
    { required: true, trigger: 'blur' }
  ],
  email: [
    { required: true, trigger: 'blur' },
  ]
})

/**
 * tree过滤
 */
const filterNode = (value: string, data: TreeNode) => {
  if (!value) return true
  return data.name.includes(value)
}

/**
 * 分页变化
 * @param currentPage 当前页码
 * @param pageSize 分页大小
 */
function pageChange(currentPage: number, pageSize: number) {
  pagination.page = currentPage
  pagination.size = pageSize
  load()
}

/**
 * 加载列表
 */
async function load() {
  loading.value = true
  retrieveUsers(pagination, filters.value).then(res => {
    datas.value = res.data.content
    total.value = res.data.page.totalElements
  }).finally(() => loading.value = false)
}

/**
 * reset
 */
function reset() {
  filters.value = {
    username: null
  }
  load()
}

onMounted(() => {
  load()
})

/**
 * 弹出框
 * @param id 主键
 */
function editRow(id?: number) {
  form.value = { ...initialValues }
  if (id) {
    loadOne(id)
  }
  dialogVisible.value = true
}

/**
 * 加载
 * @param id 主键
 */
async function loadOne(id: number) {
  fetchUser(id).then(res => {
    form.value = res.data
  })
}

/**
 * 表单提交
 */
function onSubmit() {
  let formEl = formRef.value
  if (!formEl) return

  formEl.validate((valid, fields) => {
    if (valid) {
      saveLoading.value = true
      if (form.value.id) {
        modifyUser(form.value.id, form.value).then(() => {
          load()
          dialogVisible.value = false
        }).finally(() => saveLoading.value = false)
      } else {
        form.value.groupId = currentNodeKey.value
        createUser(form.value).then(() => {
          load()
          dialogVisible.value = false
        }).finally(() => saveLoading.value = false)
      }
    }
  })
}

/**
 * 删除
 * @param id 主键
 */
function removeRow(id: number) {
  removeUser(id).then(() => load())
}

/**
 * 确认
 * @param id 主键
 */
function confirmEvent(id: number) {
  if (id) {
    removeRow(id)
  }
}

/**
 * 解锁/上锁
 * @param row 数据
 */
function lockRow(row: User) {
  row.accountNonLocked = !row.accountNonLocked
}

/**
 * 全选操作
 * @param val 是否全选
 */
function handleCheckAllChange(val: boolean) {
  checkedColumns.value = val ? columns.value : []
  isIndeterminate.value = false
}

/**
 * 选中操作
 * @param value 选中的值
 */
function handleCheckedChange(value: string[]) {
  const checkedCount = value.length
  checkAll.value = checkedCount === columns.value.length
  isIndeterminate.value = checkedCount > 0 && checkedCount < columns.value.length
}
</script>

<template>

  <ElSpace size="large" direction="vertical" fill class="w-full">
    <ElCard shadow="never">
      <ElForm inline :model="filters">
        <ElFormItem :label="$t('username')" prop="username">
          <ElInput v-model="filters.username" :placeholder="$t('inputText') + $t('username')" />
        </ElFormItem>
        <ElFormItem>
          <ElButton type="primary" @click="load">
            <div class="i-material-symbols:search-rounded" />{{ $t('search') }}
          </ElButton>
          <ElButton @click="reset">
            <div class="i-material-symbols:replay-rounded" />{{ $t('reset') }}
          </ElButton>
        </ElFormItem>
      </ElForm>
    </ElCard>

    <ElCard shadow="never">
      <ElRow :gutter="20" justify="space-between" class="mb-4">
        <ElCol :span="16" class="text-left">
          <ElButton type="primary" @click="editRow()">
            <div class="i-material-symbols:add-rounded" />{{ $t('add') }}
          </ElButton>
          <ElButton type="warning" plain @click="dialogVisible = true">
            <div class="i-material-symbols:upload-file-outline-rounded" />{{ $t('import') }}
          </ElButton>
          <ElButton type="success" plain>
            <div class="i-material-symbols:file-save-outline-rounded" />{{ $t('export') }}
          </ElButton>
        </ElCol>

        <ElCol :span="8" class="text-right">
          <ElTooltip effect="dark" :content="$t('refresh')" placement="top">
            <ElButton type="primary" plain circle @click="load">
              <div class="i-material-symbols:refresh-rounded" />
            </ElButton>
          </ElTooltip>

          <ElTooltip :content="$t('column') + $t('settings')" placement="top">
            <span class="inline-block ml-3 h-8">
              <ElPopover :width="200" trigger="click">
                <template #reference>
                  <ElButton type="success" plain circle>
                    <div class="i-material-symbols:format-list-bulleted" />
                  </ElButton>
                </template>
                <div>
                  <ElCheckbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">
                    全选
                  </ElCheckbox>
                  <ElDivider />
                  <ElCheckboxGroup v-model="checkedColumns" @change="handleCheckedChange">
                    <draggable v-model="columns" item-key="simple">
                      <template #item="{ element }">
                        <div class="flex items-center space-x-2">
                          <div class="i-material-symbols:drag-indicator w-4 h-4 hover:cursor-move" />
                          <ElCheckbox :label="element" :value="element" :disabled="element === columns[0]">
                            <div class="inline-flex items-center space-x-4">
                              {{ $t(element) }}
                            </div>
                          </ElCheckbox>
                        </div>
                      </template>
                    </draggable>
                  </ElCheckboxGroup>
                </div>
              </ElPopover>
            </span>
          </ElTooltip>
        </ElCol>
      </ElRow>

      <ElTable v-loading="loading" :data="datas" lazy :load="load" row-key="id" stripe table-layout="auto">
        <ElTableColumn type="selection" width="55" />
        <ElTableColumn type="index" :label="$t('no')" width="55" />
        <ElTableColumn show-overflow-tooltip prop="username" :label="$t('username')">
          <template #default="scope">
            <div class="flex items-center">
              <ElAvatar :size="30" :src="scope.row.avatar" class="flex-shrink-0" />
              <div class="ml-2 inline-flex flex-col">
                <span class="text-sm">{{ scope.row.fullName }}</span>
                <span class="text-xs text-[var(--el-text-color-secondary)]">{{ scope.row.username }}</span>
              </div>
            </div>
          </template>
        </ElTableColumn>
        <ElTableColumn prop="email" :label="$t('email')" />
        <ElTableColumn prop="accountNonLocked" :label="$t('accountNonLocked')">
          <template #default="scope">
            <div
              :class="['cursor-pointer', scope.row.accountNonLocked ? 'i-material-symbols:lock-open-right-outline-rounded text-[var(--el-color-success)]' : 'i-material-symbols:lock-outline text-[var(--el-color-warning)]']"
              @click="lockRow(scope.row)" />
          </template>
        </ElTableColumn>
        <ElTableColumn prop="enabled" :label="$t('enabled')">
          <template #default="scope">
            <ElSwitch size="small" v-model="scope.row.enabled" style="--el-switch-on-color: var(--el-color-success);" />
          </template>
        </ElTableColumn>
        <ElTableColumn prop="accountExpiresAt" :label="$t('accountExpiresAt')">
          <template #default="scope">
            {{ scope.row.accountExpiresAt ? dayjs(scope.row.accountExpiresAt).format('YYYY-MM-DD HH:mm:ss') : '-' }}
          </template>
        </ElTableColumn>
        <ElTableColumn prop="encredentialsExpiresAtbled" :label="$t('credentialsExpiresAt')">
          <template #default="scope">
            {{ scope.row.credentialsExpiresAt ? dayjs(scope.row.credentialsExpiresAt).format('YYYY-MM-DD HH:mm:ss') :
              '-'
            }}
          </template>
        </ElTableColumn>
        <ElTableColumn :label="$t('actions')">
          <template #default="scope">
            <ElButton size="small" type="primary" link @click="editRow(scope.row.id)">
              <div class="i-material-symbols:edit-outline-rounded" />{{ $t('edit') }}
            </ElButton>
            <ElPopconfirm :title="$t('removeConfirm')" :width="240" @confirm="confirmEvent(scope.row.id)">
              <template #reference>
                <ElButton size="small" type="danger" link>
                  <div class="i-material-symbols:delete-outline-rounded" />{{ $t('remove') }}
                </ElButton>
              </template>
            </ElPopconfirm>
          </template>
        </ElTableColumn>
      </ElTable>
      <ElPagination layout="prev, pager, next, sizes, jumper, ->, total" @change="pageChange" :total="total" />
    </ElCard>
  </ElSpace>

  <Dialog v-model="dialogVisible" :title="$t('users')" width="25%">
    <ElForm ref="formRef" :model="form" :rules="rules" label-position="top">
      <ElRow :gutter="20" class="w-full !mx-0">
        <ElCol :span="24">
          <ElFormItem :label="$t('fullName')" prop="fullName">
            <ElInput type="email" v-model="form.fullName" :placeholder="$t('inputText') + $t('fullName')"
              :maxLength="50" />
          </ElFormItem>
        </ElCol>
      </ElRow>
      <ElRow :gutter="20" class="w-full !mx-0">
        <ElCol :span="24">
          <ElFormItem :label="$t('username')" prop="username">
            <ElInput v-model="form.username" :placeholder="$t('inputText') + $t('username')" :maxLength="50" />
          </ElFormItem>
        </ElCol>
      </ElRow>
      <ElRow :gutter="20" class="w-full !mx-0">
        <ElCol :span="24">
          <ElFormItem :label="$t('email')" prop="email">
            <ElInput type="email" v-model="form.email" :placeholder="$t('inputText') + $t('email')" :maxLength="50" />
          </ElFormItem>
        </ElCol>
      </ElRow>
    </ElForm>
    <template #footer>
      <ElButton @click="dialogVisible = false">
        <div class="i-material-symbols:close" />{{ $t('cancel') }}
      </ElButton>
      <ElButton type="primary" :loading="saveLoading" @click="onSubmit">
        <div class="i-material-symbols:check-circle-outline-rounded" /> {{ $t('submit') }}
      </ElButton>
    </template>
  </Dialog>
</template>
