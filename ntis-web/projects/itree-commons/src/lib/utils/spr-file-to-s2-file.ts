import { S2File } from '@itree/ngx-s2-ui';
import { SprFile } from '../model/api/api';

export const sprFileToS2File = (sprFile: SprFile): S2File => {
  return (
    sprFile && {
      name: sprFile.fil_name,
      type: sprFile.fil_content_type,
      date: sprFile.fil_status_date,
      size: sprFile.fil_size,
      uploaded: !!sprFile.fil_key,
      key: sprFile.fil_key,
    }
  );
};
