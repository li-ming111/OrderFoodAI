<script setup lang="ts">
import { computed,ref } from 'vue'
import type { CartItem } from '../types'
import axios from 'axios'
import type { Order } from '../types'
import { message } from 'ant-design-vue';

const props = defineProps<{items: CartItem[]}>()

const emit = defineEmits<{
  (e: 'update-quantity', itemId: number, quantity: number): void
  (e: 'remove-item', itemId: number): void
}>()

const showOrderModal = ref(false)
const orderForm = ref({
  customerName: '',
  customerPhone: '',
  deliveryAddress: '',
  notes: ''
})

const totalPrice = computed(() => {
  return props.items.reduce((total, item) => {
    return total + item.price * item.quantity
  }, 0)
})

function handleSubmitOrder() {
  if (!orderForm.value.customerName || !orderForm.value.customerPhone || !orderForm.value.deliveryAddress) {
    
    message.warning('请填写完整的订单信息');
    return
  }
  // 提交订单的请求数据格式示例：
  const requestRequest = {
    ...orderForm.value,
    items: props.items.map(item => ({dishId:item.id,dashName: item.name, quantity: item.quantity}))
  }
  // 这里可以添加提交订单的逻辑
  axios.post('/api/orders',requestRequest).then(()=>{
    message.success('订单提交成功！');
  })
  showOrderModal.value = false
}
// 智能点餐参数
let aiDish = ref('')
const orders = ref<Order | null>(null)
// 智能点餐处理函数
function dishHandler() {
  // 在这里实现智能点餐的逻辑
  // 可以使用props.items中的数据，并根据需要进行处理
  // 处理完成后，可以调用emit函数来触发父组件的事件
  // 例如，你可以将处理后的数据存储在orders.value中
  axios.post('/api/orders/natural-language',{naturalLanguageInput:aiDish.value}).then(res=>{
    orders.value = res.data
  })
}


function updateQuantity(itemId: number, quantity: number) {
  emit('update-quantity', itemId, quantity)
}

function removeItem(itemId: number) {
  emit('remove-item', itemId)
}
</script>

<template>
  <a-card class="cart">
    <h2>智能购物车</h2>
    <a-row>
      <a-col :span="24">
        <a-textarea
          v-model:value="aiDish"
          :rows="8"
          style="font-size: large;"
          placeholder="请输入您的点餐要求，例如：我的姓名是张三,联系电话1393630000,配送地址是洪南路180号,我要两份宫保鸡丁，一份麻婆豆腐..."
        />
        <br>
        <a-button type="primary" @click="dishHandler" style="margin-top: 16px;">智能点餐</a-button>
        <br>
        <br>
        <a-divider />
        <div v-if="orders">
          <!-- 智能点餐列表 -->
          <a-card v-for="item in orders.orderItems" :key="item.id" style="margin-bottom: 16px;">
            <a-row :gutter="16">
              <a-col :span="12">
                <h3>{{ item.dishName }}</h3>
                <p>份数: {{ item.quantity }}</p>
              </a-col>
              <a-col :span="12">
                <p>单价: ¥{{ item.price }}</p>
                <p class="subtotal" style="color: red;">小计: ¥{{ item.price * item.quantity }}</p>
              </a-col>
            </a-row>
          </a-card>
          <br>
          <a-card>
            <div class="ai-price">
              点餐客户:{{ orders.customerName }}<br>
              联系方式:{{ orders.customerPhone }}<br>
              配送地址:{{ orders.deliveryAddress }}<br>
              <p style="color: red;">合计金额:¥{{ orders.totalAmount }}</p>
            </div>
          </a-card>
        </div>
      </a-col>
    </a-row>
    <a-divider />
    <h2>购物车</h2>
    <a-empty v-if="items.length === 0" description="购物车为空" />
    <div v-else>
      <a-card v-for="item in items" :key="item.id" style="margin-bottom: 16px;">
        <a-row :gutter="16">
          <a-col :span="12">
            <h3>{{ item.name }}</h3>
            <p>单价: ¥{{ item.price }}</p>
          </a-col>
          <a-col :span="8">
            <a-space>
              <a-button @click="updateQuantity(item.id, item.quantity - 1)">-</a-button>
              <a-input-number
                :value="item.quantity"
                @change="(value: number) => updateQuantity(item.id, value)"
                :min="1"
              />
              <a-button @click="updateQuantity(item.id, item.quantity + 1)">+</a-button>
            </a-space>
          </a-col>
          <a-col :span="4">
            <p class="subtotal">小计: ¥{{ item.price * item.quantity }}</p>
            <a-button type="primary" danger @click="removeItem(item.id)">删除</a-button>
          </a-col>
        </a-row>
      </a-card>
      <a-card>
        <div class="total-price">
          总价: ¥{{ totalPrice }}
          <br>
          <a-button 
            type="primary" 
            style="margin-top: 16px;"
            @click="showOrderModal = true"
          >
            下单结算
          </a-button>
          <a-modal
            v-model:visible="showOrderModal"
            title="填写订单信息"
            ok-text="提交订单"
            cancel-text="取消"
            @ok="handleSubmitOrder"
          >
            <a-form
              :model="orderForm"
              :label-col="{ span: 6 }"
              :wrapper-col="{ span: 18 }"
            >
              <a-form-item label="姓名" required>
                <a-input v-model:value="orderForm.customerName" />
              </a-form-item>
              <a-form-item label="联系电话" required>
                <a-input v-model:value="orderForm.customerPhone" />
              </a-form-item>
              <a-form-item label="配送地址" required>
                <a-input v-model:value="orderForm.deliveryAddress" />
              </a-form-item>
              <a-form-item label="特殊要求">
                <a-textarea v-model:value="orderForm.notes" :rows="4" />
              </a-form-item>
            </a-form>
          </a-modal>
        </div>
      </a-card>
    </div>
  </a-card>
</template>

<style scoped>
.cart {
  padding: 20px;
  border-left: 1px solid #ddd;
}

.empty-cart {
  text-align: center;
  padding: 20px;
  color: #666;
}

.cart-item {
  display: flex;
  align-items: center;
  gap: 20px;
  padding: 15px 0;
  border-bottom: 1px solid #eee;
}

.item-info {
  flex: 1;
}

.quantity-control {
  display: flex;
  align-items: center;
  gap: 8px;
}

.quantity-control button {
  width: 30px;
  height: 30px;
  border: 1px solid #ddd;
  border-radius: 4px;
  background-color: white;
  cursor: pointer;
}

.quantity-control input {
  width: 50px;
  padding: 4px;
  text-align: center;
}

.subtotal {
  min-width: 100px;
  text-align: right;
}

.dish-btn {
  background-color: #1a3aef;
  color: white;
  border: none;
  padding: 12px 100px;
  border-radius: 4px;
  cursor: pointer;
}
.ai-btn {
  background-color: #0c5003;
  color: white;
  border: none;
  padding: 12px 100px;
  border-radius: 4px;
  cursor: pointer;
}
.remove-btn {
  background-color: #ff4444;
  color: white;
  border: none;
  padding: 6px 12px;
  border-radius: 4px;
  cursor: pointer;
}

.remove-btn:hover {
  background-color: #cc0000;
}

.total-price {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #ddd;
  font-size: 1.2em;
  font-weight: bold;
  text-align: right;
}
.ai-price {
  margin-top: 20px;
  padding-top: 20px;
  border-top: 1px solid #ddd;
  font-size: 1.2em;
  font-weight: bold;
  text-align: left;
}
</style>
