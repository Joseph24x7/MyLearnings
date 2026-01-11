# React.js Interview Questions (Basic Level)
## Based on Document Summary Application Frontend

---

## 1. React Fundamentals

### Q1: What are React Hooks and which ones are used in this application?
**Answer:** React Hooks are functions that let you use state and lifecycle features in functional components. This application uses:
- `useState` - for managing component state (e.g., sessions, messages, file, loading states)
- `useEffect` - for side effects like loading data and WebSocket connections
- `useRef` - for maintaining references to DOM elements and mutable values
- `useCallback` - for memoizing callback functions to prevent unnecessary re-renders

**Example from code:**
```jsx
const [view, setView] = useState('sessions');
const [chatSession, setChatSession] = useState(null);
```

---

### Q2: What is the purpose of useState hook?
**Answer:** `useState` is a Hook that lets you add state to functional components. It returns an array with two elements: the current state value and a function to update it.

**Example from SessionList.jsx:**
```jsx
const [sessions, setSessions] = useState([]);
const [searchActive, setSearchActive] = useState(false);
const [connected, setConnected] = useState(false);
```

---

### Q3: What is useEffect and when does it run?
**Answer:** `useEffect` is a Hook that performs side effects in functional components. It runs after the component renders and can be controlled with a dependency array.

**Examples from code:**
```jsx
// Runs once on mount (empty dependency array)
useEffect(() => {
  loadRecentSessions();
}, []);

// Runs when sessionId changes
useEffect(() => {
  loadChatHistory();
}, [sessionId]);
```

---

### Q4: What is the purpose of useRef hook?
**Answer:** `useRef` creates a mutable reference that persists across re-renders without causing re-renders when changed. Used for:
- Accessing DOM elements directly
- Storing mutable values that don't need to trigger re-renders

**Examples from code:**
```jsx
const fileInputRef = useRef(null);
const messagesEndRef = useRef(null);
const activeTokenRef = useRef(0);

// Usage
fileInputRef.current?.click();
messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
```

---

### Q5: What is useCallback and why is it used?
**Answer:** `useCallback` returns a memoized version of a callback function that only changes if dependencies change. It prevents unnecessary re-renders by maintaining function reference equality.

**Example from SessionList.jsx:**
```jsx
const handleSearch = useCallback(
  async (query) => {
    if (!connected || !stompClientRef.current) return;
    // ... search logic
  },
  [connected]
);
```

---

## 2. Component Structure & Props

### Q6: How do you pass data from parent to child components?
**Answer:** Data is passed through props. The parent component provides values or functions as props, and the child receives them.

**Example from App.jsx:**
```jsx
// Parent passing props to child
<SessionList
  onSelectSession={handleSelectSession}
  onUploadNew={handleUploadNew}
/>

// Child receiving props
export default function SessionList({ onSelectSession, onUploadNew }) {
  // Use the props
}
```

---

### Q7: What are callback props and how are they used?
**Answer:** Callback props are functions passed from parent to child that allow the child to communicate back to the parent.

**Example:**
```jsx
// Parent defines callback
const handleUploadComplete = (uploadResponse) => {
  setChatSession({ ... });
  setView('chat');
};

// Parent passes it to child
<DocumentUpload onUploadComplete={handleUploadComplete} />

// Child calls it
onUploadComplete?.(response);
```

---

### Q8: What is conditional rendering in React?
**Answer:** Conditional rendering allows you to render different UI based on conditions using JavaScript operators like `&&`, `||`, or ternary operators.

**Examples from App.jsx:**
```jsx
{view === 'sessions' && <SessionList ... />}
{view === 'upload' && <DocumentUpload ... />}
{view === 'chat' && chatSession && <ChatBot ... />}

// Ternary operator
{response ? <SuccessView /> : <FormView />}
```

---

### Q9: How do you render lists in React?
**Answer:** Use the `map()` method to transform an array into JSX elements. Each element needs a unique `key` prop.

**Example from SessionList.jsx:**
```jsx
{sessions.map((s) => (
  <button
    key={s.id || s.sessionId}
    className="session-search-item"
    onClick={() => onSelectSession(s)}
  >
    <div className="session-search-item-title">
      {s.documentName || 'Untitled Document'}
    </div>
  </button>
))}
```

---

### Q10: Why is the 'key' prop important in lists?
**Answer:** The `key` prop helps React identify which items have changed, been added, or removed, enabling efficient re-rendering. Keys should be unique among siblings.

**Example:**
```jsx
{messages.map((msg, idx) => (
  <div key={idx} className={`message ${msg.role}`}>
    {msg.content}
  </div>
))}
```

---

## 3. State Management

### Q11: How do you update state in React?
**Answer:** Use the setter function returned by `useState`. Never modify state directly.

**Examples:**
```jsx
// Wrong
sessions.push(newSession);

// Correct
setSessions([...sessions, newSession]);
setSessions((prev) => [...prev, newSession]);
```

---

### Q12: What is the functional update form of setState?
**Answer:** It's when you pass a function to the state setter instead of a value. The function receives the previous state and returns the new state. Useful when new state depends on old state.

**Example from ChatBot.jsx:**
```jsx
// Add message to existing messages
setMessages((prev) => [...prev, userMsg]);

// Remove last message
setMessages((prev) => prev.slice(0, -1));
```

---

### Q13: How do you manage multiple related state values?
**Answer:** You can use multiple `useState` calls for different pieces of state or use a single `useState` with an object.

**Example from DocumentUpload.jsx:**
```jsx
const [file, setFile] = useState(null);
const [query, setQuery] = useState('');
const [loading, setLoading] = useState(false);
const [error, setError] = useState(null);
```

---

## 4. Event Handling

### Q14: How do you handle events in React?
**Answer:** Pass event handler functions to JSX elements using camelCase naming (onClick, onChange, etc.). Use arrow functions or bind to preserve `this` context.

**Examples from ChatBot.jsx:**
```jsx
<button onClick={handleSendMessage} disabled={loading}>
  Send
</button>

<textarea
  onChange={(e) => setInputValue(e.target.value)}
  onKeyDown={handleKeyDown}
/>
```

---

### Q15: How do you prevent default behavior in event handlers?
**Answer:** Call `e.preventDefault()` on the event object.

**Example from DocumentUpload.jsx:**
```jsx
const handleSubmit = async (e) => {
  e.preventDefault();
  // Form submission logic
};

const handleDrag = (e) => {
  e.preventDefault();
  e.stopPropagation();
};
```

---

### Q16: How do you handle keyboard events?
**Answer:** Use onKeyDown, onKeyPress, or onKeyUp event handlers and check the event's key property.

**Example from ChatBot.jsx:**
```jsx
const handleKeyDown = (e) => {
  if (e.key === 'Enter' && !e.shiftKey) {
    e.preventDefault();
    handleSendMessage(e);
  }
};
```

---

## 5. Forms and Input Handling

### Q17: How do you create controlled components?
**Answer:** A controlled component's form data is handled by React state. The input value is controlled by state, and updates happen via onChange.

**Example from ChatBot.jsx:**
```jsx
const [inputValue, setInputValue] = useState('');

<textarea
  value={inputValue}
  onChange={(e) => setInputValue(e.target.value)}
/>
```

---

### Q18: How do you handle file uploads in React?
**Answer:** Use an input with `type="file"` and handle the file through the onChange event using `e.target.files`.

**Example from DocumentUpload.jsx:**
```jsx
const handleFileChange = (e) => {
  const selectedFile = e.target.files?.[0];
  if (selectedFile) {
    validateAndSetFile(selectedFile);
  }
};

<input
  type="file"
  accept=".pdf"
  onChange={handleFileChange}
/>
```

---

### Q19: How do you implement drag and drop in React?
**Answer:** Use drag event handlers like onDragEnter, onDragLeave, onDrop, and onDragOver.

**Example from DocumentUpload.jsx:**
```jsx
<div
  onDragEnter={handleDragEnter}
  onDragLeave={handleDragLeave}
  onDragOver={handleDrag}
  onDrop={handleDrop}
>
  {/* Drop zone content */}
</div>

const handleDrop = (e) => {
  e.preventDefault();
  const droppedFile = e.dataTransfer.files?.[0];
  if (droppedFile) {
    validateAndSetFile(droppedFile);
  }
};
```

---

### Q20: How do you clear a file input?
**Answer:** Use a ref to access the input element and set its value to an empty string.

**Example from DocumentUpload.jsx:**
```jsx
const fileInputRef = useRef(null);

const handleReset = () => {
  if (fileInputRef.current) {
    fileInputRef.current.value = '';
  }
};
```

---

## 6. Side Effects and Lifecycle

### Q21: How do you perform cleanup in useEffect?
**Answer:** Return a cleanup function from useEffect. It runs when the component unmounts or before the effect re-runs.

**Example from SessionList.jsx:**
```jsx
useEffect(() => {
  const client = new Client({ /* config */ });
  client.activate();

  return () => {
    client.deactivate(); // Cleanup
  };
}, []);
```

---

### Q22: How do you fetch data when a component mounts?
**Answer:** Use useEffect with an empty dependency array `[]` to run once on mount.

**Example from ChatBot.jsx:**
```jsx
useEffect(() => {
  loadChatHistory();
}, [sessionId]);

const loadChatHistory = async () => {
  try {
    const response = await getChatSession(sessionId);
    setMessages(response.data.messages || []);
  } catch (err) {
    setError('Failed to load chat history');
  }
};
```

---

### Q23: How do you scroll to an element automatically?
**Answer:** Use useRef to get the element reference and useEffect to trigger scrolling when needed.

**Example from ChatBot.jsx:**
```jsx
const messagesEndRef = useRef(null);

useEffect(() => {
  messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
}, [messages]);

<div ref={messagesEndRef} />
```

---

## 7. Async Operations

### Q24: How do you handle asynchronous operations in React?
**Answer:** Use async/await inside event handlers or useEffect, with try/catch for error handling. Use loading states to show feedback.

**Example from DocumentUpload.jsx:**
```jsx
const [loading, setLoading] = useState(false);

const handleSubmit = async (e) => {
  e.preventDefault();
  setLoading(true);
  
  try {
    const result = await uploadDocument(file, query);
    setResponse(result.data);
  } catch (err) {
    setError(err.message);
  } finally {
    setLoading(false);
  }
};
```

---

### Q25: How do you show loading states?
**Answer:** Use boolean state to track loading status and conditionally render loading UI.

**Example from ChatBot.jsx:**
```jsx
{loading && (
  <div className="message assistant">
    <div className="ds-typing">
      <div className="ds-typing-dot"></div>
      <div className="ds-typing-dot"></div>
      <div className="ds-typing-dot"></div>
    </div>
  </div>
)}
```

---

## 8. Component Communication

### Q26: How do components communicate with each other?
**Answer:**
- Parent to Child: Via props
- Child to Parent: Via callback props
- Sibling to Sibling: Through shared parent state

**Example from App.jsx:**
```jsx
// Parent manages state
const [chatSession, setChatSession] = useState(null);

// Child updates parent state via callback
const handleSelectSession = (session) => {
  setChatSession(session);
  setView('chat');
};

// Pass callback to child
<SessionList onSelectSession={handleSelectSession} />
```

---

### Q27: What is prop drilling and how is it demonstrated?
**Answer:** Prop drilling is passing props through multiple component layers. In this app, session data flows from App → through components → to ChatBot.

**Example:**
```jsx
// App.jsx
<ChatBot
  sessionId={chatSession.sessionId}
  documentName={chatSession.documentName}
  onReset={handleReset}
/>

// ChatBot uses these props directly
export default function ChatBot({ sessionId, documentName, onReset }) {
  // Use the props
}
```

---

## 9. Conditional Logic

### Q28: How do you show/hide elements conditionally?
**Answer:** Use logical operators or ternary expressions.

**Examples:**
```jsx
// AND operator
{!searchActive && recentSessions.length > 0 && (
  <section>Recent sessions</section>
)}

// Ternary operator
{file ? <FileInfo /> : <DropZone />}

// Optional chaining
onUploadComplete?.(response);
```

---

### Q29: How do you handle empty states?
**Answer:** Check if data is empty and render appropriate UI.

**Example from SessionList.jsx:**
```jsx
{!searchActive && recentSessions.length === 0 && (
  <div className="ds-empty-state">
    <div className="ds-empty-state-icon">📭</div>
    <div className="ds-empty-state-title">No sessions yet</div>
    <div className="ds-empty-state-text">
      Upload a document to get started
    </div>
  </div>
)}
```

---

## 10. Best Practices

### Q30: How do you organize component files?
**Answer:** Group related files together:
- Component JSX file
- Component CSS file
- Keep reusable components in a components folder
- Keep API calls in separate service files

**Structure from the app:**
```
src/
  components/
    ChatBot.jsx
    ChatBot.css
    DocumentUpload.jsx
    DocumentUpload.css
  api/
    documentApi.js
```

---

### Q31: How do you handle error states?
**Answer:** Use state to store error messages and display them to users.

**Example from ChatBot.jsx:**
```jsx
const [error, setError] = useState(null);

try {
  const response = await sendChatMessage(sessionId, userMessage);
} catch (err) {
  const errorMessage = err.response?.data?.message || 
                       'Failed to send message';
  setError(`❌ ${errorMessage}`);
}

{error && (
  <div className="error-toast">
    {error}
    <span onClick={() => setError(null)}>✕</span>
  </div>
)}
```

---

### Q32: How do you disable buttons conditionally?
**Answer:** Use the `disabled` prop with boolean conditions.

**Examples:**
```jsx
<button
  disabled={loading || !inputValue.trim()}
>
  Send
</button>

<button
  disabled={!file || loading}
>
  Upload
</button>
```

---

### Q33: What is the optional chaining operator (?.) and how is it used?
**Answer:** It safely accesses nested object properties without throwing errors if a property is undefined or null.

**Examples from code:**
```jsx
fileInputRef.current?.click();
onUploadComplete?.(response);
const droppedFile = e.dataTransfer.files?.[0];
messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
```

---

### Q34: How do you implement debouncing in React?
**Answer:** Use setTimeout with useRef to store the timeout ID and clear it on subsequent calls.

**Example from SearchInput.jsx:**
```jsx
const debounceRef = useRef(null);

useEffect(() => {
  if (debounceRef.current) {
    clearTimeout(debounceRef.current);
  }

  debounceRef.current = setTimeout(async () => {
    await onSearch(query);
  }, 400);

  return () => clearTimeout(debounceRef.current);
}, [query]);
```

---

### Q35: How do you handle environment variables in React (Vite)?
**Answer:** Use `import.meta.env` to access environment variables prefixed with `VITE_`.

**Example from documentApi.js:**
```jsx
const API_BASE_URL = import.meta.env.MODE === 'development' 
  ? 'http://localhost:8080' 
  : '';
```

---

## 11. Styling in React

### Q36: How do you apply CSS classes conditionally?
**Answer:** Use template literals or ternary operators to apply classes based on conditions.

**Examples:**
```jsx
<div className={`message ${msg.role}`}>

<div className={`upload-dropzone ${dragOver ? 'drag-over' : ''} 
                ${file ? 'has-file' : ''}`}>

<div className={`message-wrapper ${msg.role}`}>
```

---

### Q37: How do you apply inline styles in React?
**Answer:** Pass a JavaScript object to the `style` prop. CSS properties use camelCase.

**Example:**
```jsx
<button 
  style={{ 
    width: '100%', 
    marginBottom: 'var(--spacing-xl)' 
  }}
>
  Upload
</button>
```

---

## 12. React Project Structure

### Q38: What is the entry point of a React application?
**Answer:** The entry point is typically `main.jsx` or `index.jsx`, which renders the root component into the DOM.

**Example from main.jsx:**
```jsx
import { StrictMode } from 'react'
import ReactDOM from 'react-dom/client'
import App from './App'
import './index.css'

ReactDOM.createRoot(document.getElementById('root')).render(
  <StrictMode>
    <App />
  </StrictMode>
)
```

---

### Q39: What is React StrictMode?
**Answer:** StrictMode is a development tool that highlights potential problems. It runs checks and warnings for its descendants.

**Usage:**
```jsx
<StrictMode>
  <App />
</StrictMode>
```

---

### Q40: How do you organize API calls in React applications?
**Answer:** Create separate service/API files to keep components clean and reusable.

**Example from documentApi.js:**
```javascript
import axios from 'axios';

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 600000,
});

export const uploadDocument = (file, query) => {
  const formData = new FormData();
  formData.append('file', file);
  return apiClient.post('/api/v1/documents/upload', formData);
};
```

---

## 13. Additional Interview Questions

### Q41: How do you make async calls to backend from React?
**Answer:** Use `fetch` API or `axios` with async/await inside useEffect or event handlers.

```jsx
// Using fetch
const fetchData = async () => {
  try {
    const response = await fetch('/api/data');
    const data = await response.json();
    setData(data);
  } catch (error) {
    setError(error.message);
  }
};

// Using axios
const fetchData = async () => {
  const { data } = await axios.get('/api/data');
  setData(data);
};

useEffect(() => { fetchData(); }, []);
```

---

### Q42: How do you handle performance issues if UI takes time to render?
**Answer:** Use these optimization techniques:

1. **React.memo** - Memoize components to prevent unnecessary re-renders
2. **useMemo** - Cache expensive calculations
3. **useCallback** - Cache function references
4. **Code Splitting** - Lazy load components
5. **Virtualization** - Render only visible items (react-window)
6. **Pagination** - Load data in chunks

```jsx
// Memoization
const MemoizedComponent = React.memo(ExpensiveComponent);

// Lazy loading
const LazyComponent = React.lazy(() => import('./HeavyComponent'));

// useMemo for expensive calculations
const sortedData = useMemo(() => data.sort(), [data]);
```

---

### Q43: How do you handle multiple screen sizes (responsive design) in React?
**Answer:** Multiple approaches:

1. **CSS Media Queries** - Most common approach
2. **CSS Flexbox/Grid** - Fluid layouts
3. **Custom Hook** - Detect screen size
4. **Libraries** - react-responsive, Material-UI breakpoints

```jsx
// Custom hook for responsive design
const useWindowSize = () => {
  const [size, setSize] = useState({ width: window.innerWidth });
  useEffect(() => {
    const handleResize = () => setSize({ width: window.innerWidth });
    window.addEventListener('resize', handleResize);
    return () => window.removeEventListener('resize', handleResize);
  }, []);
  return size;
};

// Usage
const { width } = useWindowSize();
return width < 768 ? <MobileView /> : <DesktopView />;
```

---

**Application Architecture Summary:**
- **App.jsx**: Main container managing view state and navigation
- **SessionList.jsx**: Lists sessions with WebSocket search functionality
- **DocumentUpload.jsx**: Handles file upload with drag-and-drop
- **ChatBot.jsx**: Real-time chat interface
- **SearchInput.jsx**: Reusable search component with debouncing
- **documentApi.js**: Centralized API calls using Axios

This is a well-structured React application demonstrating modern React patterns and best practices! 🚀