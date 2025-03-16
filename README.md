# API Documentation

## User Authentication & Management

### 1. Register User
**Endpoint:** `POST /api/v1/auth/register`

**Request Body:**
```json
{
  "fullName": "John Doe",
  "username": "johndoe",
  "email": "johndoe@example.com",
  "password": "securepassword",
  "provider": "local"
}
```

**Response:**
```json
{
  "message": "User registered successfully",
  "timestamp": "2025-03-16T10:00:00Z",
  "success": true
}
```

---
### 2. Login User
**Endpoint:** `POST /api/v1/auth/login`

**Request Body:**
```json
{
  "emailOrUsername": "johndoe",
  "password": "securepassword"
}
```

**Response:**
```json
{
  "token": "jwt-token-here",
  "type": "Bearer "
}
```

---
### 3. Verify OTP
**Endpoint:** `POST /api/v1/auth/verify-otp`

**Request Body:**
```json
{
  "email": "johndoe@example.com",
  "otp": "123456"
}
```

**Response:**
```json
{
  "message": "OTP verified successfully",
  "timestamp": "2025-03-16T10:05:00Z",
  "success": true
}
```

---
### 4. Resend OTP
**Endpoint:** `POST /api/v1/auth/resend-otp`

**Request Body:**
```json
{
  "email": "johndoe@example.com"
}
```

**Response:**
```json
{
  "message": "OTP sent successfully",
  "timestamp": "2025-03-16T10:06:00Z",
  "success": true
}
```

---
### 5. Update User Profile
**Endpoint:** `PUT /api/v1/user/profile`

**Request Body:**
```json
{
  "fullName": "John Doe Updated",
  "username": "johndoe",
  "email": "johndoe@example.com",
  "bio": "Software Developer",
  "countryName": "USA",
  "city": "New York",
  "recoveryPhone": "+1234567890",
  "recoveryEmail": "backup@example.com",
  "socialLinks": {
    "github": "https://github.com/johndoe"
  },
  "jobTitle": "Backend Developer",
  "company": "TechCorp",
  "website": "https://johndoe.dev"
}
```

**Response:**
```json
{
  "message": "Profile updated successfully",
  "timestamp": "2025-03-16T10:10:00Z",
  "success": true
}
```

---
## Premium & Redeem Code Management

### 6. Redeem Premium Code
**Endpoint:** `POST /api/v1/premium/redeem`

**Request Body:**
```json
{
  "redeemCode": "PREMIUM1234"
}
```

**Response:**
```json
{
  "id": "987654321",
  "redeemCode": "PREMIUM1234",
  "redeemedBy": "johndoe",
  "redeemedAt": "2025-03-16T10:15:00Z",
  "createdAt": "2025-03-15T09:00:00Z",
  "used": true
}
```

---
### 7. Get User Role
**Endpoint:** `GET /api/v1/user/role/{username}`

**Response:**
```json
{
  "id": "12345",
  "roleName": "USER",
  "username": "johndoe"
}
```

---
## General Response Format
All APIs return responses in the following standard format:

```json
{
  "message": "Success or error message",
  "timestamp": "ISO 8601 timestamp",
  "success": true or false
}
```