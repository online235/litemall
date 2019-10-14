<template>
  <div class="app-container">

    <el-card class="box-card">
      <h3>商品介绍</h3>
      <el-form ref="goods" :rules="rules" :model="litemallmerchant" label-width="150px">
        <el-form-item label="商户名称" prop="username">
          <el-input v-model="litemallmerchant.username"/>
        </el-form-item>
        <el-form-item label="手机号码" prop="phone">
          <el-input v-model="litemallmerchant.phone"/>
        </el-form-item>
      </el-form>
    </el-card>
    <div class="op-container">
      <el-button @click="handleCancel">关闭</el-button>
      <el-button type="primary" @click="handlePublish">添加</el-button>
    </div>

  </div>
</template>



<script>
    import { addMerChant } from '@/api/user'
    import Editor from '@tinymce/tinymce-vue'
    import { MessageBox } from 'element-ui'

    export default {
        name: 'addmerchant',
        components: { Editor },

        data() {
            return {
                litemallmerchant: { picUrl: '', gallery: [] },
                rules: {
                    username: [{ required: true, message: '商户名称不能为空', trigger: 'blur' }],
                    phone: [{ required: true, message: '手机号码不能为空', trigger: 'blur' }]
                },

            }
        },

        methods: {
            handleCancel: function() {
                this.$router.push({ path: '/user/merchant' })
            },
            handlePublish: function() {
                const finalmer = {
                    username: this.litemallmerchant.username,
                    phone: this.litemallmerchant.phone
                }
                addMerChant(finalmer).then(response => {
                    this.$notify.success({
                        title: '成功',
                        message: '创建成功'
                    })
                    this.$router.push({ path: '/user/merchant' })
                }).catch(response => {
                    MessageBox.alert('业务错误：' + response.data.errmsg, '警告', {
                        confirmButtonText: '确定',
                        type: 'error'
                    })
                })
            },
        }
    }
</script>
<style>
  .el-card {
    margin-bottom: 10px;
  }

  .el-tag + .el-tag {
    margin-left: 10px;
  }

  .input-new-keyword {
    width: 90px;
    margin-left: 10px;
    vertical-align: bottom;
  }

  .avatar-uploader .el-upload {
    width: 145px;
    height: 145px;
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }

  .avatar-uploader .el-upload:hover {
    border-color: #20a0ff;
  }

  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 120px;
    height: 120px;
    line-height: 120px;
    text-align: center;
  }

  .avatar {
    width: 145px;
    height: 145px;
    display: block;
  }
</style>
