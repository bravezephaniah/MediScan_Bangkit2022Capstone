
# MediScan API

API code for MediScan Application.
## Tech Stack

**Server:** Node, Hapi

**Database:** MySQL


## Environment Variables

To run this project, you will need to add the following environment variables to your .env file

`ACCESS_TOKEN_SECRET`

`DB_HOST`

`DB_USER`

`DB_PASSWORD`

`DB_DATABASE`


## Run Locally

Clone the project

```bash
  git clone https://github.com/denisadfer/mediscan-api.git
```

Go to the project directory

```bash
  cd mediscan-api
```

Install dependencies

```bash
  npm install
```

Start the server on development

```bash
  npm run start-dev
```

Start the server on production

```bash
  npm run start-prod
```

## API Reference

#### Register User

```http
  POST /users
```

| Request | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required** |
| `email` | `string` | **Required** |
| `password` | `string` | **Required** |

#### Login User

```http
  POST /login
```

| Request | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username`      | `string` | **Required** |
| `password` | `string` | **Required** |

#### Logout User

```http
  POST /logout
```

| Request | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `token` | `string` | **Required** |

#### Update User Profile

```http
  PUT /users
```
**Authorization** Bearer {token}

| Request | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `username` | `string` | **Required** |
| `email` | `string` | **Required** |
| `password` | `string` | **Required** |

#### Change User Password

```http
  PUT /users/changePassword
```
**Authorization** Bearer {token}

| Request | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `password` | `string` | **Required** |
| `newPassword` | `string` | **Required** |

#### Add History Data

```http
  POST /history
```
**Authorization** Bearer {token}

| Request | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `result` | `string` | **Required** |
| `file` | `file (image)` | **Required** |

#### Get History By UserId

```http
  GET /history
```
**Authorization** Bearer {token}

#### Delete History By Id

```http
  DELETE /history/{id}
```
**Authorization** Bearer {token}

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `id` | `string` | **Required** |

