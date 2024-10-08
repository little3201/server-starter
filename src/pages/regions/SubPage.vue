<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import type { FormInstance, FormRules } from 'element-plus'
import Dialog from 'components/Dialog.vue'
import { retrieveRegions, fetchRegion, createRegion, modifyRegion, removeRegion } from 'src/api/regions'
import type { Region } from 'src/models'

const props = defineProps<{
  superiorId: number,
  title: string
}>()

const loading = ref<boolean>(false)
const datas = ref<Array<Region>>([])
const total = ref<number>(0)
const pagination = reactive<Pagination>({
  page: 1,
  size: 10,
  sortBy: 'id',
  descending: false
})

const saveLoading = ref<boolean>(false)
const dialogVisible = ref<boolean>(false)

const formRef = ref<FormInstance>()
const initialValues: Region = {
  name: '',
  superiorId: props.superiorId,
  areaCode: null,
  postalCode: null,
  description: ''
}
const form = ref<Region>({ ...initialValues })

const rules = reactive<FormRules<typeof form>>({
  name: [
    { required: true, trigger: 'blur' }
  ]
})

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
  retrieveRegions(pagination, { superiorId: props.superiorId }).then(res => {
    datas.value = res.data.content
    total.value = res.data.page.totalElements
  }).finally(() => loading.value = false)

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
  fetchRegion(id).then(res => {
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
        modifyRegion(form.value.id, form.value).then(() => {
          load()
          dialogVisible.value = false
        }).finally(() => saveLoading.value = false)
      } else {
        form.value.superiorId = props.superiorId
        createRegion(form.value).then(() => {
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
  removeRegion(id).then(() => load())
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
</script>

<template>
  <ElCard shadow="never">
    <ElRow :gutter="20" justify="end" align="middle" class="mb-4">
      <ElCol :span="12" class="text-left">
        <span class="text-xl">{{ title }}</span>
      </ElCol>
      <ElCol :span="12" class="text-right">
        <ElButton type="primary" @click="editRow()">
          <div class="i-material-symbols:add-rounded" />{{ $t('add') }}
        </ElButton>
        <ElTooltip class="box-item" effect="dark" :content="$t('refresh')" placement="top">
          <ElButton type="primary" plain circle @click="load">
            <div class="i-material-symbols:refresh-rounded" />
          </ElButton>
        </ElTooltip>
      </ElCol>
    </ElRow>

    <ElTable v-loading="loading" :data="datas" lazy :load="load" row-key="id" stripe table-layout="auto">
      <ElTableColumn type="selection" width="55" />
      <ElTableColumn type="expand">
        <template #default="props">
          <SubPage :superior-id="props.row.id" :title="props.row.name" />
        </template>
      </ElTableColumn>
      <ElTableColumn prop="name" :label="$t('name')" />
      <ElTableColumn prop="areaCode" :label="$t('areaCode')" />
      <ElTableColumn prop="postalCode" :label="$t('postalCode')" />
      <ElTableColumn prop="enabled" :label="$t('enabled')">
        <template #default="scope">
          <ElSwitch size="small" v-model="scope.row.enabled" style="--el-switch-on-color: var(--el-color-success);" />
        </template>
      </ElTableColumn>
      <ElTableColumn show-overflow-tooltip prop="description" :label="$t('description')" />
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

  <Dialog v-model="dialogVisible" :title="$t('regions')" width="25%">
    <ElForm ref="formRef" :model="form" :rules="rules" label-position="top">
      <ElRow :gutter="20" class="w-full !mx-0">
        <ElCol>
          <ElFormItem :label="$t('name')" prop="name">
            <ElInput v-model="form.name" :placeholder="$t('inputText') + $t('name')" />
          </ElFormItem>
        </ElCol>
      </ElRow>
      <ElRow :gutter="20" class="w-full !mx-0">
        <ElCol>
          <ElFormItem :label="$t('areaCode')" prop="areaCode">
            <ElInput v-model="form.areaCode" :placeholder="$t('inputText') + $t('areaCode')" />
          </ElFormItem>
        </ElCol>
      </ElRow>
      <ElRow :gutter="20" class="w-full !mx-0">
        <ElCol>
          <ElFormItem :label="$t('postalCode')" prop="postalCode">
            <ElInput v-model="form.postalCode" :placeholder="$t('inputText') + $t('postalCode')" />
          </ElFormItem>
        </ElCol>
      </ElRow>
      <ElRow :gutter="20" class="w-full !mx-0">
        <ElCol>
          <ElFormItem :label="$t('description')" prop="description">
            <ElInput v-model="form.description" type="textarea" :placeholder="$t('inputText') + $t('description')" />
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

<style lang="scss" scoped>
.el-card {
  border: none !important;
}
</style>