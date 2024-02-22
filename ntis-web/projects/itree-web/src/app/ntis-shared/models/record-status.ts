export interface StatusData {
  statusText?: string;
  status: string;
  iconName?: string;
  type: 'success' | 'risk' | 'warn' | 'neutral';
  iconClass?: string;
}
