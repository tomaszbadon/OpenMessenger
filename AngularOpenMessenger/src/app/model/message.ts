export interface Message {
    id: number;
    message: string;
    isAcknowledged: boolean;
    sender: number;
    recipient: number;
    sentAt: string;
}