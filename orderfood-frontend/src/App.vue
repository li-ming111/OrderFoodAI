<script setup lang="ts">
import { ref } from 'vue'
import Menu from './components/Menu.vue'
import Cart from './components/Cart.vue'
import type { MenuItem, CartItem } from './types'

const cartItems = ref<CartItem[]>([])

function addToCart(item: MenuItem) {
  const existingItem = cartItems.value.find(i => i.id === item.id)
  if (existingItem) {
    existingItem.quantity += 1
  } else {
    cartItems.value.push({ ...item, quantity: 1 })
    console.log('------------->>', cartItems.value);
  }
}

function updateQuantity(itemId: number, quantity: number) {
  const item = cartItems.value.find(i => i.id === itemId)
  if (item) {
    if (quantity < 1) {
      removeItem(itemId)
    } else {
      item.quantity = quantity
    }
  }
}

function removeItem(itemId: number) {
  cartItems.value = cartItems.value.filter(i => i.id !== itemId)
}
</script>

<template>
  <a-layout class="app-container">
    <a-layout-header>
      <a-typography-title :level="1" style="color: white; margin: 0">
        在线点餐系统
      </a-typography-title>
    </a-layout-header>
    <a-layout-content style="padding: 24px">
      <a-row :gutter="[24, 24]">
        <a-col :span="16">
          <Menu @add-to-cart="addToCart" />
        </a-col>
        <a-col :span="8">
          <Cart 
            :items="cartItems"
            @update-quantity="updateQuantity"
            @remove-item="removeItem"
          />
        </a-col>
      </a-row>
    </a-layout-content>
  </a-layout>
</template>

<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: Arial, sans-serif;
}

.app-container {
  min-height: 100vh;
}

.ant-layout-header {
  background-color: #42b983 !important;
  padding: 0 24px;
  line-height: 64px;
}

@media (max-width: 768px) {
  .ant-col {
    width: 100% !important;
    max-width: 100% !important;
  }
}
</style>
