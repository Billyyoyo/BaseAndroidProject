<template>
  <mu-container>
    <mu-tabs :value.sync="active">
      <mu-tab v-for="(channel, i) in channels" :key="i">{{channel}}</mu-tab>
    </mu-tabs>
    <div class="demo-text" v-for="(channel, i) in channels" :key="i" v-if="active === i">
      <List :channel="channel"></List>
    </div>
  </mu-container>
</template>

<script>
  // import {api} from '../api'
  import List from '../components/List'

  export default {
    name: 'News',
    components: {List},
    data () {
      return {
        active: 0,
        channels: ['财经',
                   '体育',
                   '娱乐',
                   '教育',
                   '科技',
                   'NBA',
                   '股票',
                   '星座',
                   '女性',
                   '健康',
                   '育儿']
      }
    },
    mounted () {
      this.loadChannels()
    },
    methods: {
      loadChannels () {
        // api.channels()
        //   .then(resp => {
        //     if (resp.data.status === '0') {
        //       this.channels = resp.data.result
        //     } else {
        //       this.$toast.error(resp.data.msg)
        //     }
        //   })
        //   .catch(err => {
        //     console.error(JSON.stringify(err))
        //   })
      }
    }
  }
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
</style>
