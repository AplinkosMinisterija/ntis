export interface SprTaskCardAssignee {
  user_id: number;
  name: string;
  surname: string;
}

export interface SprTaskCard {
  tas_id: number;
  tas_name: string;
  tas_priority: number;
  tas_description: string;
  tas_status: string;
  tas_date_to: Date;
  assignees: SprTaskCardAssignee[];
  progress: number;
}
