# Trading Application Backend API Documentation

This repository contains the backend API for a trading application. It provides endpoints for user authentication, asset management, coin information, order processing, payment handling, and wallet management.

## API Endpoints

### 1. Authentication

#### User Registration
**POST** `/auth/signup`

**Request Body:**
```json
{
  "fullName": "John Doe",
  "email": "john.doe@example.com",
  "password": "securepassword"
}
```

**Response:**
```json
{
  "jwt": "...",
  "status": true,
  "message": "Registration successful"
}
```

#### User Login
**POST** `/auth/signin`

**Request Body:**
```json
{
  "email": "john.doe@example.com",
  "password": "securepassword"
}
```

**Response:**
```json
{
  "jwt": "...",
  "status": true,
  "message": "Login successful"
}
```

---

### 2. Asset Management

#### Retrieve Asset by ID
**GET** `/api/asset/{assetId}`

**Response:**
```json
{
  "id": 1,
  "coinId": "bitcoin",
  "quantity": 0.5,
  "user": { "id": 123, "email": "user@example.com" }
}
```

---

### 3. Coin Information

#### Fetch List of Coins
**GET** `/coins?page={page}`

**Response:**
```json
[
  {
    "id": "bitcoin",
    "symbol": "BTC",
    "name": "Bitcoin",
    "image": "url_to_image",
    "current_price": 50000.0
  }
]
```

#### Retrieve Market Chart Data
**GET** `/coins/{coinId}/chart?days={days}`

**Response:**
```json
{
  "prices": [
    [1678886400000, 49000.0],
    [1678972800000, 49500.0]
  ]
}
```

---

### 4. Order Management

#### Execute an Order
**POST** `/api/orders/pay`

**Request Body:**
```json
{
  "coinId": "bitcoin",
  "quantity": 0.5,
  "orderType": "BUY"
}
```

**Response:**
```json
{
  "id": 1,
  "coin": { "id": "bitcoin", "symbol": "BTC" },
  "quantity": 0.5,
  "orderType": "BUY",
  "user": { "id": 123, "email": "user@example.com" }
}
```

---

### 5. Payment Processing

#### Initiate Payment
**POST** `/api/payment/create`

**Request Body:**
```json
{
  "amount": 1000,
  "currency": "INR"
}
```

**Response:**
```json
{
  "paymentId": "abc123",
  "status": "PENDING"
}
```

---

### 6. Wallet Management

#### Retrieve Wallet Balance
**GET** `/api/wallet/{userId}`

**Response:**
```json
{
  "userId": 123,
  "balance": 5000.0
}
```

#### Retrieve Transaction History
**GET** `/api/transactions/{userId}`

**Response:**
```json
[
  {
    "transactionId": "txn001",
    "type": "DEPOSIT",
    "amount": 5000,
    "status": "COMPLETED"
  }
]
```

---

## Contact
For inquiries, reach out to `pdprasantapd46245@gmail.com`.
