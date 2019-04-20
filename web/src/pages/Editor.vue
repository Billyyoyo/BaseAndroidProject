<template>
  <div class="edit_container">
    <quill-editor
      v-model="content"
      ref="myQuillEditor"
      :options="editorOption"
      @blur="onEditorBlur($event)"
      @focus="onEditorFocus($event)"
      @change="onEditorChange($event)"
    ></quill-editor>
  </div>
</template>
<script>
  import { quillEditor } from 'vue-quill-editor' // 调用编辑器
  import 'quill/dist/quill.core.css'
  import 'quill/dist/quill.snow.css'
  import 'quill/dist/quill.bubble.css'

  export default {
    components: {
      quillEditor
    },
    data () {
      return {
        content: `<p></p><p><br></p><ol><li><strong><em>Or drag/paste an image here.</em>
        </strong></li><li><strong><em>rerew</em></strong></li>
        <li><strong><em>rtrete</em></strong></li><li><strong><em>tytrytr</em></strong></li>
        <li><strong><em>uytu</em></strong></li></ol>`,
        editorOption: {
          modules: {
            toolbar: [
              [{ 'header': 1 }, { 'header': 2 }, 'bold', 'italic', 'blockquote', 'code-block', { list: 'ordered' }, { list: 'bullet' }, 'link', 'image']
            ],
            history: {
              delay: 1000,
              maxStack: 50,
              userOnly: false
            },
            imageDrop: true,
            imageResize: {
              displayStyles: {
                backgroundColor: 'black',
                border: 'none',
                color: 'white'
              },
              modules: ['Resize', 'DisplaySize', 'Toolbar']
            }
          }
        }
      }
    },
    methods: {
      onEditorReady (editor) {
        // 准备编辑器
      },
      onEditorBlur () {}, // 失去焦点事件
      onEditorFocus () {}, // 获得焦点事件
      onEditorChange () {} // 内容改变事件
    },
    computed: {
      editor () {
        return this.$refs.myQuillEditor.quill
      }
    },
    mounted () {
      let self = this
      window.getHtml = function () {
        return self.content
      }
    }
  }
</script>
<style scoped>
.quill-editor {
    height: 100%;
  }
.ql-snow {
  border: 1px none #ccc;
}
.edit_container{
  height: 100%;
}
</style>
