<script setup lang="ts">
import { onMounted, ref } from 'vue'
import type { MenuItem } from '../types'
import axios from 'axios'

const menuItems = ref<MenuItem[]>([])

const show = () => {
  axios.get<MenuItem[]>('/api/dishes').then((res) => {
    console.log('----------->>>', res.data[0].imageUrl)
    menuItems.value = res.data
  })
}

onMounted(() => {
  show()
})

const emit = defineEmits<{
  (e: 'add-to-cart', item: MenuItem): void
}>()

function addToCart(item: MenuItem) {
  emit('add-to-cart', item)
}
</script>

<template>
  <a-row :gutter="[24, 24]">
    <a-col 
      v-for="item in menuItems" 
      :key="item.id" 
      :xs="24" 
      :sm="12"
    >
      <a-card hoverable>
        <a-row :gutter="16" align="middle">
          <a-col :span="8">
            <a-image
              :src="'http://localhost:8080/'+item.imageUrl"
              :alt="item.name"
              :width="100"
              :height="100"
              :preview="false"
              style="border-radius: 8px"
            />
          </a-col>
          <a-col :span="16">
            <a-typography-title :level="4" style="margin: 0">
              {{ item.name }}
            </a-typography-title>
            <a-typography-paragraph type="secondary">
              {{ item.description }}
            </a-typography-paragraph>
            <a-typography-text strong>
              ¥{{ item.price }}
            </a-typography-text>
            <div style="margin-top: 12px">
              <a-button 
                type="primary" 
                @click="addToCart(item)"
              >
                加入购物车
              </a-button>
            </div>
          </a-col>
        </a-row>
      </a-card>
    </a-col>
  </a-row>
</template>

<style scoped>
.ant-card {
  border-radius: 8px;
}

.ant-card:hover {
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
}
</style>
