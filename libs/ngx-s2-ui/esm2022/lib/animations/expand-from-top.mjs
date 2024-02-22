import { animate, style, transition, trigger } from '@angular/animations';
export const expandFromTop = (name = 'expandFromTop') => {
    return trigger(name, [
        transition(':enter', [
            style({
                opacity: 0,
                transform: 'scaleY(0)',
                'transform-origin': 'top',
                height: 0,
            }),
            animate('0.15s linear', style({
                opacity: 1,
                transform: 'scaleY(1)',
                height: '*',
            })),
        ]),
        transition(':leave', [
            style({
                opacity: 1,
                transform: 'scaleY(1)',
                'transform-origin': 'top',
                height: '*',
            }),
            animate('0.15s linear', style({ opacity: 0, transform: 'scaleY(0)', height: 0 })),
        ]),
    ]);
};
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiZXhwYW5kLWZyb20tdG9wLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vLi4vcHJvamVjdHMvbmd4LXMyLXVpL3NyYy9saWIvYW5pbWF0aW9ucy9leHBhbmQtZnJvbS10b3AudHMiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEsT0FBTyxFQUE0QixPQUFPLEVBQUUsS0FBSyxFQUFFLFVBQVUsRUFBRSxPQUFPLEVBQUUsTUFBTSxxQkFBcUIsQ0FBQztBQUVwRyxNQUFNLENBQUMsTUFBTSxhQUFhLEdBQUcsQ0FBQyxJQUFJLEdBQUcsZUFBZSxFQUE0QixFQUFFO0lBQ2hGLE9BQU8sT0FBTyxDQUFDLElBQUksRUFBRTtRQUNuQixVQUFVLENBQUMsUUFBUSxFQUFFO1lBQ25CLEtBQUssQ0FBQztnQkFDSixPQUFPLEVBQUUsQ0FBQztnQkFDVixTQUFTLEVBQUUsV0FBVztnQkFDdEIsa0JBQWtCLEVBQUUsS0FBSztnQkFDekIsTUFBTSxFQUFFLENBQUM7YUFDVixDQUFDO1lBQ0YsT0FBTyxDQUNMLGNBQWMsRUFDZCxLQUFLLENBQUM7Z0JBQ0osT0FBTyxFQUFFLENBQUM7Z0JBQ1YsU0FBUyxFQUFFLFdBQVc7Z0JBQ3RCLE1BQU0sRUFBRSxHQUFHO2FBQ1osQ0FBQyxDQUNIO1NBQ0YsQ0FBQztRQUNGLFVBQVUsQ0FBQyxRQUFRLEVBQUU7WUFDbkIsS0FBSyxDQUFDO2dCQUNKLE9BQU8sRUFBRSxDQUFDO2dCQUNWLFNBQVMsRUFBRSxXQUFXO2dCQUN0QixrQkFBa0IsRUFBRSxLQUFLO2dCQUN6QixNQUFNLEVBQUUsR0FBRzthQUNaLENBQUM7WUFDRixPQUFPLENBQUMsY0FBYyxFQUFFLEtBQUssQ0FBQyxFQUFFLE9BQU8sRUFBRSxDQUFDLEVBQUUsU0FBUyxFQUFFLFdBQVcsRUFBRSxNQUFNLEVBQUUsQ0FBQyxFQUFFLENBQUMsQ0FBQztTQUNsRixDQUFDO0tBQ0gsQ0FBQyxDQUFDO0FBQ0wsQ0FBQyxDQUFDIiwic291cmNlc0NvbnRlbnQiOlsiaW1wb3J0IHsgQW5pbWF0aW9uVHJpZ2dlck1ldGFkYXRhLCBhbmltYXRlLCBzdHlsZSwgdHJhbnNpdGlvbiwgdHJpZ2dlciB9IGZyb20gJ0Bhbmd1bGFyL2FuaW1hdGlvbnMnO1xyXG5cclxuZXhwb3J0IGNvbnN0IGV4cGFuZEZyb21Ub3AgPSAobmFtZSA9ICdleHBhbmRGcm9tVG9wJyk6IEFuaW1hdGlvblRyaWdnZXJNZXRhZGF0YSA9PiB7XHJcbiAgcmV0dXJuIHRyaWdnZXIobmFtZSwgW1xyXG4gICAgdHJhbnNpdGlvbignOmVudGVyJywgW1xyXG4gICAgICBzdHlsZSh7XHJcbiAgICAgICAgb3BhY2l0eTogMCxcclxuICAgICAgICB0cmFuc2Zvcm06ICdzY2FsZVkoMCknLFxyXG4gICAgICAgICd0cmFuc2Zvcm0tb3JpZ2luJzogJ3RvcCcsXHJcbiAgICAgICAgaGVpZ2h0OiAwLFxyXG4gICAgICB9KSxcclxuICAgICAgYW5pbWF0ZShcclxuICAgICAgICAnMC4xNXMgbGluZWFyJyxcclxuICAgICAgICBzdHlsZSh7XHJcbiAgICAgICAgICBvcGFjaXR5OiAxLFxyXG4gICAgICAgICAgdHJhbnNmb3JtOiAnc2NhbGVZKDEpJyxcclxuICAgICAgICAgIGhlaWdodDogJyonLFxyXG4gICAgICAgIH0pXHJcbiAgICAgICksXHJcbiAgICBdKSxcclxuICAgIHRyYW5zaXRpb24oJzpsZWF2ZScsIFtcclxuICAgICAgc3R5bGUoe1xyXG4gICAgICAgIG9wYWNpdHk6IDEsXHJcbiAgICAgICAgdHJhbnNmb3JtOiAnc2NhbGVZKDEpJyxcclxuICAgICAgICAndHJhbnNmb3JtLW9yaWdpbic6ICd0b3AnLFxyXG4gICAgICAgIGhlaWdodDogJyonLFxyXG4gICAgICB9KSxcclxuICAgICAgYW5pbWF0ZSgnMC4xNXMgbGluZWFyJywgc3R5bGUoeyBvcGFjaXR5OiAwLCB0cmFuc2Zvcm06ICdzY2FsZVkoMCknLCBoZWlnaHQ6IDAgfSkpLFxyXG4gICAgXSksXHJcbiAgXSk7XHJcbn07XHJcbiJdfQ==