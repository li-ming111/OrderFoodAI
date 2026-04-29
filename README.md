# 🍜 OrderFoodAI - 智能在线点餐系统

基于 **Spring AI + DeepSeek** 大模型驱动的智能点餐系统，支持自然语言点餐、菜品浏览、购物车管理等功能。

## ✨ 功能特性

- 🤖 **AI 智能点餐** — 通过 DeepSeek 大模型理解自然语言点餐需求，自动转换为结构化订单
- 📋 **菜品展示** — 菜单列表浏览，支持图片展示与价格显示
- 🛒 **购物车管理** — 添加/修改/删除商品，实时计算总价
- 💾 **订单持久化** — 基于 MySQL + MyBatis-Plus 实现数据存储
- 📱 **响应式布局** — 前端支持 PC 和移动端自适应

## 🏗️ 技术栈

### 后端 (orderfood-backend)
| 技术 | 版本 | 说明 |
|------|------|------|
| Java | 21 | JDK 21 |
| Spring Boot | 3.4.7 | 基础框架 |
| Spring AI | 1.0.0 | AI 集成框架 |
| DeepSeek API | - | 大语言模型（智能点餐） |
| MyBatis-Plus | 3.5.12 | ORM 框架 |
| MySQL | - | 数据库 |
| Lombok | 1.18.38 | 简化代码 |

### 前端 (orderfood-frontend)
| 技术 | 版本 | 说明 |
|------|------|------|
| Vue 3 | ^3.5.13 | 渐进式框架 |
| TypeScript | ~5.6.2 | 类型安全 |
| Ant Design Vue | ^4.2.6 | UI 组件库 |
| Vite | ^6.0.5 | 构建工具 |
| Axios | ^1.7.9 | HTTP 请求 |

## 📁 项目结构

```
orderfood/
├── orderfood-backend/          # 后端 Spring Boot 项目
│   ├── src/main/java/com/SIM/
│   │   ├── TakeoutApplication.java      # 启动类
│   │   ├── ai/service/                  # AI 点餐服务（DeepSeek）
│   │   │   └── impl/DeepseekAiOrderService.java
│   │   ├── controller/                  # 控制器层
│   │   │   ├── DishController.java
│   │   │   └── OrderController.java
│   │   ├── domain/                      # 实体类
│   │   ├── dto/                         # 数据传输对象
│   │   ├── mapper/                      # MyBatis-Plus Mapper
│   │   └── service/                     # 业务逻辑层
│   └── src/main/resources/
│       ├── application.yaml             # 配置文件
│       ├── data/dish.txt                # 菜品数据
│       └── static/images/               # 菜品图片
│
├── orderfood-frontend/         # 前端 Vue3 项目
│   ├── src/
│   │   ├── App.vue             # 根组件
│   │   ├── components/
│   │   │   ├── Menu.vue        # 菜单组件
│   │   │   └── Cart.vue        # 购物车组件
│   │   └── types.ts            # TypeScript 类型定义
│   └── vite.config.ts          # Vite 配置（含代理）
```

## 🚀 快速开始

### 环境要求
- **JDK 21+**
- **Node.js 18+**
- **MySQL 5.7+ / MySQL 8.0**
- **DeepSeek API Key**

### 1️⃣ 数据库配置

创建数据库并导入 SQL：
```sql
CREATE DATABASE food DEFAULT CHARACTER SET utf8mb4;
-- 导入 orderfood-backend/food.sql
```

### 2️⃣ 后端启动

```bash
cd orderfood-backend

# 配置环境变量（或修改 application.yaml）
export DEEPSEEK_API_KEY=your-api-key-here

# 启动项目
./mvnw spring-boot:run
# 或 IDEA 直接运行 TakeoutApplication.java
```
后端默认运行在 `http://localhost:8080`

### 3️⃣ 前端启动

```bash
cd orderfood-frontend

# 安装依赖
npm install

# 开发模式启动
npm run dev
```
前端默认运行在 `http://localhost:5173`（已配置 `/api` 代理到后端）

## 🔧 核心配置

### application.yaml 关键配置
```yaml
spring:
  ai:
    deepseek:
      api-key: ${DEEPSEEK_API_KEY}     # DeepSeek API 密钥
      base-url: https://api.deepseek.com
      chat:
        options:
          model: deepseek-chat           # 使用 deepseek-chat 模型
  datasource:
    url: jdbc:mysql://localhost:3306/food
    username: root
    password: root                       # 修改为你的密码
```

## 🌐 接口说明

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/dishes` | 获取所有菜品列表 |
| POST | `/order` | 提交订单 |
| POST | `/ai/order` | AI 智能点餐（自然语言输入） |

## 📝 使用示例

发送自然语言到 `/ai/order` 接口：
```json
// 请求：我要一份宫保鸡丁和两瓶可乐
// 返回：
{
  "items": [
    { "dishId": 1, "quantity": 1 },
    { "dishId": 5, "quantity": 2 }
  ]
}
```

## License

MIT
