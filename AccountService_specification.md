# Account Service
## 1. User register API
### 1.1 Path
> /account/register <br />
### 1.2 Method
> POST
### 1.3 Headers
> none
### 1.4 Cookies
> none
### 1.5 Parameters detail
> JSON:<br />
```json
{
   "username": "xxxx",
   "password": "xxxx",
   "confirmPassword": "xxxx"
}
```

| Param Name | Type | Location | mandatory | memo |
| :---: | :---: | :---: | :---: | :---: |
| username | string | body | true | the username to be registry, length: 3-12, only can use A-Z,a-z,0-9 and _ |
| password | string | body | true | the login password, length 6-18 |
| confirmPassword | string | body | true | the same as password |

### 1.6 Return
> JSON:<br />

```json
{
    "retCode": xxxx,
    "retMsg": "xxxx"
}
```

> retCode: retMsg
>
> - 10000: register successful
> - 20001: username is null
> - 20002: password is null
> - 20003: the length of the username is invalid
> - 20004: username contains illegal characters
> - 20005: the length of the password is invalid
> - 20006: Confirm-password is not same as password
> - 20007: username is already exist

### 1.7 Example

xxxx



## 2. User Login API

### 2.1 Path

> /account/login<br />

### 2.2 Method

> POST

### 2.3 Headers

> none

### 2.4 Cookies

> none

### 2.5 Parameters detail

> Form-data:

| Param Name | Type   | Location | mandatory | memo |
| ---------- | ------ | -------- | --------- | ---- |
| username   | string | body     | true      |      |
| password   | string | body     | true      |      |

### 2.6 Return

> JSON:<br />

```json
{
    "retCode": xxxx,
    "retMsg": "xxxx",
    "data": {
    	"token": "xxxx",
        "expiredTime": "time"
	}
}
```

> retCode: retMsg
>
> - 10000: login successful
> - 20008: username or password is invalid

### 2.7 Example

xxxx

## 3. Check Token API

### 3.1 Path

> /account/check_token<br />

### 3.2 Method

> GET

### 3.3 Headers

> none

### 3.4 Cookies

> none

### 3.5 Parameters detail

> Form-data:

| Param Name | Type   | Location    | mandatory | memo |
| ---------- | ------ | ----------- | --------- | ---- |
| token      | string | queryString | true      |      |

### 3.6 Return

> JSON:<br />

```json
{
    "retCode": xxxx,
    "retMsg": "xxxx",
    "data": null
}
```

> retCode: retMsg
>
> - 10000: Token is valid
> - 20009: Token is invalid
> - 20010: Token is out of date

### 3.7 Example

xxxx