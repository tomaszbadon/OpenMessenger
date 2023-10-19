import { LeftSide } from '../components/LeftSide';
import Conversation from '../components/Conversation';
import './Chat.sass'

export default function Chat() {
  return (
    <div className='box box-grid box-80-80'>
      <LeftSide />
      <Conversation />
      <div className='message-input-box'>
        <input type='text' placeholder='Type a message...' />
        <button><img src='/assets/envelope.png' height='16px' alt='send' /></button>
      </div>
    </div>
  );
}