# NUDG API 명세서

## 기본 정보

- **Base URL**: `https://api.nudg.app/v1`
- **인증 방식**: Bearer Token (JWT)
- **Content-Type**: `application/json`
- **응답 형식**: JSON

---

## 인증

### 로그인
```
POST /auth/login
```

**Request Body:**
```json
{
  "email": "string",
  "password": "string"
}
```

**Response:**
```json
{
  "token": "string",
  "user": {
    "id": "number",
    "email": "string",
    "name": "string"
  }
}
```

### 회원가입
```
POST /auth/register
```

**Request Body:**
```json
{
  "email": "string",
  "password": "string",
  "name": "string"
}
```

### 토큰 갱신
```
POST /auth/refresh
```

**Request Body:**
```json
{
  "refreshToken": "string"
}
```

---

## 목표 (Goals)

### 목표 목록 조회
```
GET /goals
```

**Query Parameters:**
- `status`: `active` | `archived` (기본값: `active`)
- `sort`: `recent` | `progress` | `name` (기본값: `recent`)
- `page`: `number` (기본값: 1)
- `limit`: `number` (기본값: 20)

**Response:**
```json
{
  "goals": [
    {
      "id": "number",
      "title": "string",
      "tags": ["string"],
      "progress": "number",
      "completedSteps": "number",
      "totalSteps": "number",
      "totalTime": "string",
      "color": "blue" | "green" | "orange" | "yellow" | "purple" | "pink" | "cyan" | "red",
      "status": "active" | "archived",
      "createdAt": "string",
      "updatedAt": "string"
    }
  ],
  "pagination": {
    "page": "number",
    "limit": "number",
    "total": "number",
    "totalPages": "number"
  }
}
```

### 목표 통계 조회
```
GET /goals/statistics
```

**Response:**
```json
{
  "totalGoals": "number",
  "completedGoals": "number",
  "inProgressGoals": "number",
  "averageProgress": "number"
}
```

### 목표 상세 조회
```
GET /goals/:id
```

**Response:**
```json
{
  "id": "number",
  "title": "string",
  "tags": ["string"],
  "progress": "number",
  "completedSteps": "number",
  "totalSteps": "number",
  "totalTime": "string",
  "color": "string",
  "status": "string",
  "steps": [
    {
      "id": "number",
      "title": "string",
      "completed": "boolean",
      "order": "number"
    }
  ],
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 목표 생성
```
POST /goals
```

**Request Body:**
```json
{
  "title": "string",
  "color": "blue" | "green" | "orange" | "yellow" | "purple" | "pink" | "cyan" | "red",
  "tags": ["string"]
}
```

**Response:**
```json
{
  "id": "number",
  "title": "string",
  "tags": ["string"],
  "progress": "number",
  "completedSteps": "number",
  "totalSteps": "number",
  "color": "string",
  "status": "active",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 목표 수정
```
PATCH /goals/:id
```

**Request Body:**
```json
{
  "title": "string",
  "color": "string",
  "tags": ["string"],
  "status": "active" | "archived"
}
```

### 목표 삭제
```
DELETE /goals/:id
```

**Response:**
```json
{
  "success": "boolean",
  "message": "string"
}
```

---

## 스텝 (Steps)

### 스텝 목록 조회
```
GET /goals/:goalId/steps
```

**Response:**
```json
{
  "steps": [
    {
      "id": "number",
      "goalId": "number",
      "title": "string",
      "completed": "boolean",
      "order": "number",
      "createdAt": "string",
      "updatedAt": "string"
    }
  ]
}
```

### 스텝 생성
```
POST /goals/:goalId/steps
```

**Request Body:**
```json
{
  "title": "string",
  "order": "number"
}
```

### 스텝 수정
```
PATCH /goals/:goalId/steps/:stepId
```

**Request Body:**
```json
{
  "title": "string",
  "completed": "boolean",
  "order": "number"
}
```

### 스텝 삭제
```
DELETE /goals/:goalId/steps/:stepId
```

### 스텝 완료 처리
```
POST /goals/:goalId/steps/:stepId/complete
```

### 스텝 완료 취소
```
POST /goals/:goalId/steps/:stepId/uncomplete
```

---

## CBT 기록 (CBT Entries)

### CBT 기록 목록 조회
```
GET /cbt/entries
```

**Query Parameters:**
- `view`: `timeline` | `list` (기본값: `timeline`)
- `period`: `today` | `week` | `month` (기본값: `today`)
- `date`: `string` (YYYY-MM-DD 형식, 선택)
- `mood`: `green` | `orange` | `red` (선택)
- `result`: `success` | `failure` (선택)
- `page`: `number` (기본값: 1)
- `limit`: `number` (기본값: 20)

**Response (timeline view):**
```json
{
  "entries": {
    "2025-12-11": [
      {
        "id": "number",
        "date": "string",
        "time": "string",
        "emoji": "string",
        "mood": "string",
        "moodColor": "green" | "orange" | "red",
        "moodScore": "number",
        "location": "string",
        "impulse": "string",
        "copingMethod": "string",
        "result": "success" | "failure",
        "notes": "string",
        "createdAt": "string",
        "updatedAt": "string"
      }
    ]
  }
}
```

**Response (list view):**
```json
{
  "entries": [
    {
      "id": "number",
      "date": "string",
      "time": "string",
      "emoji": "string",
      "mood": "string",
      "moodColor": "string",
      "location": "string",
      "impulse": "string",
      "copingMethod": "string",
      "result": "string",
      "createdAt": "string",
      "updatedAt": "string"
    }
  ],
  "pagination": {
    "page": "number",
    "limit": "number",
    "total": "number",
    "totalPages": "number"
  }
}
```

### CBT 기록 상세 조회
```
GET /cbt/entries/:id
```

**Response:**
```json
{
  "id": "number",
  "date": "string",
  "time": "string",
  "emoji": "string",
  "mood": "string",
  "moodScore": "number",
  "moodColor": "string",
  "title": "string",
  "impulse": "string",
  "copingMethod": "string",
  "location": "string",
  "result": "success" | "failure",
  "notes": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### CBT 기록 생성 (빠른 기록)
```
POST /cbt/entries
```

**Request Body:**
```json
{
  "moodScore": "number", // -5 ~ 5
  "impulse": "string",
  "copingMethod": "string", // "breathing" | "walk" | "water" | "meditation" | "music" | "custom"
  "location": "string",
  "notes": "string"
}
```

**Response:**
```json
{
  "id": "number",
  "date": "string",
  "time": "string",
  "emoji": "string",
  "mood": "string",
  "moodColor": "string",
  "impulse": "string",
  "copingMethod": "string",
  "result": "string",
  "createdAt": "string"
}
```

### CBT 기록 수정
```
PATCH /cbt/entries/:id
```

**Request Body:**
```json
{
  "moodScore": "number",
  "impulse": "string",
  "copingMethod": "string",
  "location": "string",
  "result": "success" | "failure",
  "notes": "string"
}
```

### CBT 기록 삭제
```
DELETE /cbt/entries/:id
```

**Response:**
```json
{
  "success": "boolean",
  "message": "string"
}
```

### CBT 통계 조회
```
GET /cbt/statistics
```

**Query Parameters:**
- `period`: `today` | `week` | `month` | `year` (기본값: `week`)

**Response:**
```json
{
  "totalEntries": "number",
  "successRate": "number",
  "moodDistribution": {
    "green": "number",
    "orange": "number",
    "red": "number"
  },
  "topCopingMethods": [
    {
      "method": "string",
      "count": "number",
      "successRate": "number"
    }
  ]
}
```

---

## 루틴 (Routines)

### 루틴 목록 조회
```
GET /routines
```

**Query Parameters:**
- `status`: `active` | `inactive` | `all` (기본값: `all`)
- `page`: `number` (기본값: 1)
- `limit`: `number` (기본값: 20)

**Response:**
```json
{
  "routines": [
    {
      "id": "number",
      "title": "string",
      "duration": "number", // 분 단위
      "time": "string", // HH:mm 형식
      "days": ["string"], // ["매일", "월", "화", ...]
      "frequency": "매일" | "매주" | "격주" | "커스텀",
      "notificationType": "string",
      "notificationMessage": "string",
      "emoji": "string",
      "active": "boolean",
      "weeklyProgress": ["number"], // [1, 1, 0, 1, ...] 최근 7일
      "timeUntil": "string",
      "createdAt": "string",
      "updatedAt": "string"
    }
  ],
  "pagination": {
    "page": "number",
    "limit": "number",
    "total": "number",
    "totalPages": "number"
  }
}
```

### 루틴 상세 조회
```
GET /routines/:id
```

**Response:**
```json
{
  "id": "number",
  "title": "string",
  "duration": "number",
  "time": "string",
  "days": ["string"],
  "frequency": "string",
  "notificationType": "string",
  "notificationMessage": "string",
  "emoji": "string",
  "active": "boolean",
  "weeklyProgress": ["number"],
  "completionHistory": [
    {
      "date": "string",
      "completed": "boolean"
    }
  ],
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 루틴 생성
```
POST /routines
```

**Request Body:**
```json
{
  "title": "string",
  "duration": "number", // 분 단위
  "time": "string", // HH:mm 형식
  "frequency": "매일" | "매주" | "격주" | "커스텀",
  "days": ["string"], // ["일", "월", "화", ...]
  "alarmEnabled": "boolean",
  "notificationType": "string",
  "notificationMessage": "string",
  "emoji": "string"
}
```

**Response:**
```json
{
  "id": "number",
  "title": "string",
  "duration": "number",
  "time": "string",
  "days": ["string"],
  "frequency": "string",
  "active": "boolean",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 루틴 수정
```
PATCH /routines/:id
```

**Request Body:**
```json
{
  "title": "string",
  "duration": "number",
  "time": "string",
  "frequency": "string",
  "days": ["string"],
  "alarmEnabled": "boolean",
  "notificationType": "string",
  "notificationMessage": "string",
  "emoji": "string"
}
```

### 루틴 삭제
```
DELETE /routines/:id
```

### 루틴 활성화/비활성화
```
PATCH /routines/:id/status
```

**Request Body:**
```json
{
  "active": "boolean"
}
```

### 루틴 완료 처리
```
POST /routines/:id/complete
```

**Request Body:**
```json
{
  "date": "string" // YYYY-MM-DD 형식, 선택 (기본값: 오늘)
}
```

**Response:**
```json
{
  "success": "boolean",
  "message": "string",
  "weeklyProgress": ["number"]
}
```

### 루틴 미루기
```
POST /routines/:id/snooze
```

**Request Body:**
```json
{
  "minutes": "number" // 미루기 시간 (분)
}
```

---

## 일정 (Schedules)

### 일정 목록 조회
```
GET /schedules
```

**Query Parameters:**
- `date`: `string` (YYYY-MM-DD 형식, 기본값: 오늘)
- `startDate`: `string` (YYYY-MM-DD 형식, 기간 조회 시작일)
- `endDate`: `string` (YYYY-MM-DD 형식, 기간 조회 종료일)
- `page`: `number` (기본값: 1)
- `limit`: `number` (기본값: 50)

**Response:**
```json
{
  "schedules": [
    {
      "id": "number",
      "title": "string",
      "startTime": "string", // HH:mm 형식
      "endTime": "string", // HH:mm 형식
      "duration": "number", // 분 단위
      "date": "string", // YYYY-MM-DD 형식
      "color": "string", // HEX 색상 코드
      "template": "string",
      "createdAt": "string",
      "updatedAt": "string"
    }
  ],
  "pagination": {
    "page": "number",
    "limit": "number",
    "total": "number",
    "totalPages": "number"
  }
}
```

### 일정 상세 조회
```
GET /schedules/:id
```

**Response:**
```json
{
  "id": "number",
  "title": "string",
  "startTime": "string",
  "endTime": "string",
  "duration": "number",
  "date": "string",
  "color": "string",
  "template": "string",
  "notes": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 일정 생성
```
POST /schedules
```

**Request Body:**
```json
{
  "title": "string",
  "startTime": "string", // HH:mm 형식
  "duration": "number", // 분 단위
  "date": "string", // YYYY-MM-DD 형식
  "color": "string", // HEX 색상 코드 (선택)
  "template": "string", // 선택
  "notes": "string" // 선택
}
```

**Response:**
```json
{
  "id": "number",
  "title": "string",
  "startTime": "string",
  "endTime": "string",
  "duration": "number",
  "date": "string",
  "color": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 일정 수정
```
PATCH /schedules/:id
```

**Request Body:**
```json
{
  "title": "string",
  "startTime": "string",
  "duration": "number",
  "date": "string",
  "color": "string",
  "template": "string",
  "notes": "string"
}
```

### 일정 삭제
```
DELETE /schedules/:id
```

### 일정 복사
```
POST /schedules/:id/copy
```

**Request Body:**
```json
{
  "targetDate": "string" // YYYY-MM-DD 형식
}
```

### 일정 템플릿 적용
```
POST /schedules/templates/apply
```

**Request Body:**
```json
{
  "templateName": "string",
  "date": "string" // YYYY-MM-DD 형식
}
```

---

## 사용자 설정 (User Settings)

### 사용자 프로필 조회
```
GET /user/profile
```

**Response:**
```json
{
  "id": "number",
  "email": "string",
  "name": "string",
  "avatar": "string",
  "timezone": "string",
  "language": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 사용자 프로필 수정
```
PATCH /user/profile
```

**Request Body:**
```json
{
  "name": "string",
  "avatar": "string",
  "timezone": "string",
  "language": "string"
}
```

### 알림 설정 조회
```
GET /user/notifications
```

**Response:**
```json
{
  "pushEnabled": "boolean",
  "soundEnabled": "boolean",
  "vibrationEnabled": "boolean",
  "routineReminders": "boolean",
  "scheduleReminders": "boolean",
  "goalReminders": "boolean",
  "activeCount": "number"
}
```

### 알림 설정 수정
```
PATCH /user/notifications
```

**Request Body:**
```json
{
  "pushEnabled": "boolean",
  "soundEnabled": "boolean",
  "vibrationEnabled": "boolean",
  "routineReminders": "boolean",
  "scheduleReminders": "boolean",
  "goalReminders": "boolean"
}
```

### 앱 설정 조회
```
GET /user/settings
```

**Response:**
```json
{
  "theme": "light" | "dark" | "auto",
  "fontSize": "small" | "medium" | "large",
  "colorScheme": "string"
}
```

### 앱 설정 수정
```
PATCH /user/settings
```

**Request Body:**
```json
{
  "theme": "light" | "dark" | "auto",
  "fontSize": "small" | "medium" | "large",
  "colorScheme": "string"
}
```

---

## 에러 응답

모든 API는 다음 형식의 에러 응답을 반환할 수 있습니다:

```json
{
  "error": {
    "code": "string",
    "message": "string",
    "details": "object"
  }
}
```

### 에러 코드

- `400 Bad Request`: 잘못된 요청
- `401 Unauthorized`: 인증 실패
- `403 Forbidden`: 권한 없음
- `404 Not Found`: 리소스를 찾을 수 없음
- `409 Conflict`: 리소스 충돌
- `422 Unprocessable Entity`: 유효성 검사 실패
- `500 Internal Server Error`: 서버 오류

---

## 공통 응답 형식

### 성공 응답
```json
{
  "success": "boolean",
  "data": "object",
  "message": "string"
}
```

### 페이지네이션
```json
{
  "pagination": {
    "page": "number",
    "limit": "number",
    "total": "number",
    "totalPages": "number",
    "hasNext": "boolean",
    "hasPrev": "boolean"
  }
}
```

---

## 날짜/시간 형식

- **날짜**: `YYYY-MM-DD` (예: `2025-12-11`)
- **시간**: `HH:mm` (예: `14:30`)
- **날짜시간**: `YYYY-MM-DDTHH:mm:ssZ` (ISO 8601 형식)

---

## 인증 헤더

모든 인증이 필요한 요청에는 다음 헤더를 포함해야 합니다:

```
Authorization: Bearer {token}
```

---

## 버전 관리

API 버전은 URL 경로에 포함됩니다:
- 현재 버전: `v1`
- 예: `https://api.nudg.app/v1/goals`

---

## Rate Limiting

- **기본 제한**: 분당 60회 요청
- **헤더**:
    - `X-RateLimit-Limit`: 허용된 요청 수
    - `X-RateLimit-Remaining`: 남은 요청 수
    - `X-RateLimit-Reset`: 제한 리셋 시간 (Unix timestamp)
