import { Injectable, Pipe } from '@angular/core';
import { getMimeTypeClass } from '../utils/get-mime-type-class';
import * as i0 from "@angular/core";
class FileTypeToIconPipe {
    static icons = {
        'application/geo+json': 'map',
        'application/json': 'data_object',
        'application/pdf': 'picture_as_pdf',
        'application/zip': 'folder_zip',
        'audio/*': 'music_note',
        'font/*': 'font_download',
        'image/*': 'imagesmode',
        'text/*': 'description',
        'text/css': 'css',
        'text/html': 'html',
        'text/javascript': 'javascript',
        'video/*': 'videocam',
    };
    transform(value, defaultIcon = 'draft') {
        return FileTypeToIconPipe.icons[value] || this.getWildcardType(value) || defaultIcon;
    }
    getWildcardType(value) {
        const wildcardTypeKey = Object.keys(FileTypeToIconPipe.icons)
            .filter((mimeType) => mimeType.endsWith('/*'))
            .find((mimeType) => getMimeTypeClass(mimeType) === getMimeTypeClass(value));
        return wildcardTypeKey && FileTypeToIconPipe.icons[wildcardTypeKey];
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: FileTypeToIconPipe, deps: [], target: i0.ɵɵFactoryTarget.Pipe });
    static ɵpipe = i0.ɵɵngDeclarePipe({ minVersion: "14.0.0", version: "16.0.2", ngImport: i0, type: FileTypeToIconPipe, isStandalone: true, name: "fileTypeToIcon" });
    static ɵprov = i0.ɵɵngDeclareInjectable({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: FileTypeToIconPipe, providedIn: 'root' });
}
export { FileTypeToIconPipe };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: FileTypeToIconPipe, decorators: [{
            type: Injectable,
            args: [{
                    providedIn: 'root',
                }]
        }, {
            type: Pipe,
            args: [{
                    name: 'fileTypeToIcon',
                    standalone: true,
                }]
        }] });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZmlsZS10eXBlLXRvLWljb24ucGlwZS5qcyIsInNvdXJjZVJvb3QiOiIiLCJzb3VyY2VzIjpbIi4uLy4uLy4uLy4uLy4uL3Byb2plY3RzL25neC1zMi11aS9zcmMvbGliL3BpcGVzL2ZpbGUtdHlwZS10by1pY29uLnBpcGUudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUFFLFVBQVUsRUFBRSxJQUFJLEVBQWlCLE1BQU0sZUFBZSxDQUFDO0FBQ2hFLE9BQU8sRUFBRSxnQkFBZ0IsRUFBRSxNQUFNLDhCQUE4QixDQUFDOztBQUVoRSxNQU9hLGtCQUFrQjtJQUM3QixNQUFNLENBQUMsS0FBSyxHQUEyQjtRQUNyQyxzQkFBc0IsRUFBRSxLQUFLO1FBQzdCLGtCQUFrQixFQUFFLGFBQWE7UUFDakMsaUJBQWlCLEVBQUUsZ0JBQWdCO1FBQ25DLGlCQUFpQixFQUFFLFlBQVk7UUFDL0IsU0FBUyxFQUFFLFlBQVk7UUFDdkIsUUFBUSxFQUFFLGVBQWU7UUFDekIsU0FBUyxFQUFFLFlBQVk7UUFDdkIsUUFBUSxFQUFFLGFBQWE7UUFDdkIsVUFBVSxFQUFFLEtBQUs7UUFDakIsV0FBVyxFQUFFLE1BQU07UUFDbkIsaUJBQWlCLEVBQUUsWUFBWTtRQUMvQixTQUFTLEVBQUUsVUFBVTtLQUN0QixDQUFDO0lBRUYsU0FBUyxDQUFDLEtBQWEsRUFBRSxXQUFXLEdBQUcsT0FBTztRQUM1QyxPQUFPLGtCQUFrQixDQUFDLEtBQUssQ0FBQyxLQUFLLENBQUMsSUFBSSxJQUFJLENBQUMsZUFBZSxDQUFDLEtBQUssQ0FBQyxJQUFJLFdBQVcsQ0FBQztJQUN2RixDQUFDO0lBRU8sZUFBZSxDQUFDLEtBQWE7UUFDbkMsTUFBTSxlQUFlLEdBQUcsTUFBTSxDQUFDLElBQUksQ0FBQyxrQkFBa0IsQ0FBQyxLQUFLLENBQUM7YUFDMUQsTUFBTSxDQUFDLENBQUMsUUFBUSxFQUFFLEVBQUUsQ0FBQyxRQUFRLENBQUMsUUFBUSxDQUFDLElBQUksQ0FBQyxDQUFDO2FBQzdDLElBQUksQ0FBQyxDQUFDLFFBQVEsRUFBRSxFQUFFLENBQUMsZ0JBQWdCLENBQUMsUUFBUSxDQUFDLEtBQUssZ0JBQWdCLENBQUMsS0FBSyxDQUFDLENBQUMsQ0FBQztRQUM5RSxPQUFPLGVBQWUsSUFBSSxrQkFBa0IsQ0FBQyxLQUFLLENBQUMsZUFBZSxDQUFDLENBQUM7SUFDdEUsQ0FBQzt1R0F6QlUsa0JBQWtCO3FHQUFsQixrQkFBa0I7MkdBQWxCLGtCQUFrQixjQU5qQixNQUFNOztTQU1QLGtCQUFrQjsyRkFBbEIsa0JBQWtCO2tCQVA5QixVQUFVO21CQUFDO29CQUNWLFVBQVUsRUFBRSxNQUFNO2lCQUNuQjs7a0JBQ0EsSUFBSTttQkFBQztvQkFDSixJQUFJLEVBQUUsZ0JBQWdCO29CQUN0QixVQUFVLEVBQUUsSUFBSTtpQkFDakIiLCJzb3VyY2VzQ29udGVudCI6WyJpbXBvcnQgeyBJbmplY3RhYmxlLCBQaXBlLCBQaXBlVHJhbnNmb3JtIH0gZnJvbSAnQGFuZ3VsYXIvY29yZSc7XHJcbmltcG9ydCB7IGdldE1pbWVUeXBlQ2xhc3MgfSBmcm9tICcuLi91dGlscy9nZXQtbWltZS10eXBlLWNsYXNzJztcclxuXHJcbkBJbmplY3RhYmxlKHtcclxuICBwcm92aWRlZEluOiAncm9vdCcsXHJcbn0pXHJcbkBQaXBlKHtcclxuICBuYW1lOiAnZmlsZVR5cGVUb0ljb24nLFxyXG4gIHN0YW5kYWxvbmU6IHRydWUsXHJcbn0pXHJcbmV4cG9ydCBjbGFzcyBGaWxlVHlwZVRvSWNvblBpcGUgaW1wbGVtZW50cyBQaXBlVHJhbnNmb3JtIHtcclxuICBzdGF0aWMgaWNvbnM6IFJlY29yZDxzdHJpbmcsIHN0cmluZz4gPSB7XHJcbiAgICAnYXBwbGljYXRpb24vZ2VvK2pzb24nOiAnbWFwJyxcclxuICAgICdhcHBsaWNhdGlvbi9qc29uJzogJ2RhdGFfb2JqZWN0JyxcclxuICAgICdhcHBsaWNhdGlvbi9wZGYnOiAncGljdHVyZV9hc19wZGYnLFxyXG4gICAgJ2FwcGxpY2F0aW9uL3ppcCc6ICdmb2xkZXJfemlwJyxcclxuICAgICdhdWRpby8qJzogJ211c2ljX25vdGUnLFxyXG4gICAgJ2ZvbnQvKic6ICdmb250X2Rvd25sb2FkJyxcclxuICAgICdpbWFnZS8qJzogJ2ltYWdlc21vZGUnLFxyXG4gICAgJ3RleHQvKic6ICdkZXNjcmlwdGlvbicsXHJcbiAgICAndGV4dC9jc3MnOiAnY3NzJyxcclxuICAgICd0ZXh0L2h0bWwnOiAnaHRtbCcsXHJcbiAgICAndGV4dC9qYXZhc2NyaXB0JzogJ2phdmFzY3JpcHQnLFxyXG4gICAgJ3ZpZGVvLyonOiAndmlkZW9jYW0nLFxyXG4gIH07XHJcblxyXG4gIHRyYW5zZm9ybSh2YWx1ZTogc3RyaW5nLCBkZWZhdWx0SWNvbiA9ICdkcmFmdCcpOiBzdHJpbmcge1xyXG4gICAgcmV0dXJuIEZpbGVUeXBlVG9JY29uUGlwZS5pY29uc1t2YWx1ZV0gfHwgdGhpcy5nZXRXaWxkY2FyZFR5cGUodmFsdWUpIHx8IGRlZmF1bHRJY29uO1xyXG4gIH1cclxuXHJcbiAgcHJpdmF0ZSBnZXRXaWxkY2FyZFR5cGUodmFsdWU6IHN0cmluZyk6IHN0cmluZyB7XHJcbiAgICBjb25zdCB3aWxkY2FyZFR5cGVLZXkgPSBPYmplY3Qua2V5cyhGaWxlVHlwZVRvSWNvblBpcGUuaWNvbnMpXHJcbiAgICAgIC5maWx0ZXIoKG1pbWVUeXBlKSA9PiBtaW1lVHlwZS5lbmRzV2l0aCgnLyonKSlcclxuICAgICAgLmZpbmQoKG1pbWVUeXBlKSA9PiBnZXRNaW1lVHlwZUNsYXNzKG1pbWVUeXBlKSA9PT0gZ2V0TWltZVR5cGVDbGFzcyh2YWx1ZSkpO1xyXG4gICAgcmV0dXJuIHdpbGRjYXJkVHlwZUtleSAmJiBGaWxlVHlwZVRvSWNvblBpcGUuaWNvbnNbd2lsZGNhcmRUeXBlS2V5XTtcclxuICB9XHJcbn1cclxuIl19