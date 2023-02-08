import { Route, Routes } from 'react-router-dom';
import './App.css';
import Chat from './components/chat';
import Login from './components/login';

function App() {
  return (
    <Routes>
        <Route exact path="/" element={<Login/>} />
        <Route exact path="/chat" element={<Chat/>} />
    </Routes>
  );
}

export default App;
