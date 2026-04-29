export interface MenuItem {
  id: number
  name: string
  description: string
  price: number
  imageUrl?: string
}

export interface CartItem extends MenuItem {
  quantity: number
}

export interface OrderItem {
  id: number
  dishName: string
  price: number
  quantity: number
}

export interface Order {
  orderItems: OrderItem[]
  customerName: string
  customerPhone: string
  deliveryAddress: string
  totalAmount: number
}
