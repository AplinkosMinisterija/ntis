import { S2File } from '@itree/ngx-s2-ui';
import { SprFile } from '../model/api/api';

export const s2FileToSprFile = (s2File: S2File): SprFile => {
  return (
    s2File && {
      fil_content_type: s2File.type,
      fil_key: s2File.key as string,
      fil_name: s2File.name,
      fil_size: s2File.size,
      fil_status: undefined,
      fil_status_date: s2File.date,
    }
  );
};
