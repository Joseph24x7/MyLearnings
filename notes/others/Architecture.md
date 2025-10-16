# Web Architecture and Security Concepts

## 1. What is CSRF?
CSRF (Cross-Site Request Forgery)
- Security vulnerability in web applications
- Example scenario:
  1. User logs into bank account
  2. Receives malicious link ("cute cat pictures")
  3. Link actually performs unauthorized transaction
  4. Attacker exploits user's active session

### Prevention
- CSRF tokens
- SameSite cookies
- Custom request headers
- Checking Origin/Referer headers

## 2. What is CORS?
CORS (Cross-Origin Resource Sharing)
- Security feature implemented by browsers
- Controls access to web resources
- Defines which domains can:
  - Make requests
  - Access responses
  - Read data

### Components
1. **Simple Requests**
   - GET, POST, HEAD methods
   - Standard headers only
   - Limited content types

2. **Preflight Requests**
   - OPTIONS method
   - Checks allowed:
     - Methods
     - Headers
     - Origins

### Common Headers
```http
Access-Control-Allow-Origin: https://trusted-site.com
Access-Control-Allow-Methods: GET, POST, OPTIONS
Access-Control-Allow-Headers: Content-Type
```

## 3. Best Practices
1. **CSRF Protection**
   - Implement tokens
   - Validate requests
   - Use secure session management

2. **CORS Configuration**
   - Limit allowed origins
   - Specify exact methods
   - Control exposed headers
   - Set appropriate credentials policy
