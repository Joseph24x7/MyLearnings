## 1. How to Handle Payment Gateway Sync and Async Calls?
### Synchronous Call (Blocking)
- Use `RestTemplate` or `WebClient.block()`
- Wait for payment confirmation before proceeding
- Suitable for real-time confirmation requirements

### Asynchronous Call (Non-Blocking)
- Use `WebClient` with reactive streams or `CompletableFuture`
- Payment gateway sends webhook callback on completion
- Store transaction status as PENDING, update via webhook