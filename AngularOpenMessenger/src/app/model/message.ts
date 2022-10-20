export interface Message {
    id: number;
    message: string;
    acknowledged: boolean;
    sender: number;
    recipient: number;
    sentAt: string;
}