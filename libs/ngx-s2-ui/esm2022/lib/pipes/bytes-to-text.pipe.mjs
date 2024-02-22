import { Pipe } from '@angular/core';
import * as i0 from "@angular/core";
class BytesToTextPipe {
    static defaultPrecision = 2;
    static defaultUnits = ['B', 'KB', 'MB', 'GB', 'TB', 'PB'];
    transform(bytes, precision = BytesToTextPipe.defaultPrecision, units = BytesToTextPipe.defaultUnits) {
        if (typeof precision !== 'number' || isNaN(precision) || !isFinite(precision) || precision < 0) {
            precision = BytesToTextPipe.defaultPrecision;
        }
        if (!units || units.length < 2) {
            units = BytesToTextPipe.defaultUnits;
        }
        const power = Math.min(Math.round(Math.log(bytes) / Math.log(1024)), units.length - 1);
        // const unitIndex = units[power];
        let unitIndex = power;
        let size = bytes / Math.pow(1024, power); // size in new units
        if (size < 0.5 && unitIndex > 0) {
            size = size * 1024;
            unitIndex = unitIndex - 1;
        }
        const roundMultiplier = precision > 0 ? Math.pow(10, precision) : 1;
        const roundedSize = Math.round(size * roundMultiplier) / roundMultiplier; // keep up to 2 decimals
        return `${roundedSize} ${units[unitIndex]}`;
    }
    static ɵfac = i0.ɵɵngDeclareFactory({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BytesToTextPipe, deps: [], target: i0.ɵɵFactoryTarget.Pipe });
    static ɵpipe = i0.ɵɵngDeclarePipe({ minVersion: "14.0.0", version: "16.0.2", ngImport: i0, type: BytesToTextPipe, isStandalone: true, name: "bytesToText" });
}
export { BytesToTextPipe };
i0.ɵɵngDeclareClassMetadata({ minVersion: "12.0.0", version: "16.0.2", ngImport: i0, type: BytesToTextPipe, decorators: [{
            type: Pipe,
            args: [{
                    name: 'bytesToText',
                    standalone: true,
                }]
        }] });
//# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJmaWxlIjoiYnl0ZXMtdG8tdGV4dC5waXBlLmpzIiwic291cmNlUm9vdCI6IiIsInNvdXJjZXMiOlsiLi4vLi4vLi4vLi4vLi4vcHJvamVjdHMvbmd4LXMyLXVpL3NyYy9saWIvcGlwZXMvYnl0ZXMtdG8tdGV4dC5waXBlLnRzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBLE9BQU8sRUFBRSxJQUFJLEVBQWlCLE1BQU0sZUFBZSxDQUFDOztBQUVwRCxNQUlhLGVBQWU7SUFDMUIsTUFBTSxDQUFVLGdCQUFnQixHQUFHLENBQUMsQ0FBQztJQUNyQyxNQUFNLENBQVUsWUFBWSxHQUFHLENBQUMsR0FBRyxFQUFFLElBQUksRUFBRSxJQUFJLEVBQUUsSUFBSSxFQUFFLElBQUksRUFBRSxJQUFJLENBQUMsQ0FBQztJQUVuRSxTQUFTLENBQ1AsS0FBYSxFQUNiLFlBQW9CLGVBQWUsQ0FBQyxnQkFBZ0IsRUFDcEQsS0FBSyxHQUFHLGVBQWUsQ0FBQyxZQUFZO1FBRXBDLElBQUksT0FBTyxTQUFTLEtBQUssUUFBUSxJQUFJLEtBQUssQ0FBQyxTQUFTLENBQUMsSUFBSSxDQUFDLFFBQVEsQ0FBQyxTQUFTLENBQUMsSUFBSSxTQUFTLEdBQUcsQ0FBQyxFQUFFO1lBQzlGLFNBQVMsR0FBRyxlQUFlLENBQUMsZ0JBQWdCLENBQUM7U0FDOUM7UUFFRCxJQUFJLENBQUMsS0FBSyxJQUFJLEtBQUssQ0FBQyxNQUFNLEdBQUcsQ0FBQyxFQUFFO1lBQzlCLEtBQUssR0FBRyxlQUFlLENBQUMsWUFBWSxDQUFDO1NBQ3RDO1FBRUQsTUFBTSxLQUFLLEdBQUcsSUFBSSxDQUFDLEdBQUcsQ0FBQyxJQUFJLENBQUMsS0FBSyxDQUFDLElBQUksQ0FBQyxHQUFHLENBQUMsS0FBSyxDQUFDLEdBQUcsSUFBSSxDQUFDLEdBQUcsQ0FBQyxJQUFJLENBQUMsQ0FBQyxFQUFFLEtBQUssQ0FBQyxNQUFNLEdBQUcsQ0FBQyxDQUFDLENBQUM7UUFDdkYsa0NBQWtDO1FBQ2xDLElBQUksU0FBUyxHQUFHLEtBQUssQ0FBQztRQUN0QixJQUFJLElBQUksR0FBRyxLQUFLLEdBQUcsSUFBSSxDQUFDLEdBQUcsQ0FBQyxJQUFJLEVBQUUsS0FBSyxDQUFDLENBQUMsQ0FBQyxvQkFBb0I7UUFFOUQsSUFBSSxJQUFJLEdBQUcsR0FBRyxJQUFJLFNBQVMsR0FBRyxDQUFDLEVBQUU7WUFDL0IsSUFBSSxHQUFHLElBQUksR0FBRyxJQUFJLENBQUM7WUFDbkIsU0FBUyxHQUFHLFNBQVMsR0FBRyxDQUFDLENBQUM7U0FDM0I7UUFFRCxNQUFNLGVBQWUsR0FBRyxTQUFTLEdBQUcsQ0FBQyxDQUFDLENBQUMsQ0FBQyxJQUFJLENBQUMsR0FBRyxDQUFDLEVBQUUsRUFBRSxTQUFTLENBQUMsQ0FBQyxDQUFDLENBQUMsQ0FBQyxDQUFDO1FBQ3BFLE1BQU0sV0FBVyxHQUFHLElBQUksQ0FBQyxLQUFLLENBQUMsSUFBSSxHQUFHLGVBQWUsQ0FBQyxHQUFHLGVBQWUsQ0FBQyxDQUFDLHdCQUF3QjtRQUVsRyxPQUFPLEdBQUcsV0FBVyxJQUFJLEtBQUssQ0FBQyxTQUFTLENBQUMsRUFBRSxDQUFDO0lBQzlDLENBQUM7dUdBL0JVLGVBQWU7cUdBQWYsZUFBZTs7U0FBZixlQUFlOzJGQUFmLGVBQWU7a0JBSjNCLElBQUk7bUJBQUM7b0JBQ0osSUFBSSxFQUFFLGFBQWE7b0JBQ25CLFVBQVUsRUFBRSxJQUFJO2lCQUNqQiIsInNvdXJjZXNDb250ZW50IjpbImltcG9ydCB7IFBpcGUsIFBpcGVUcmFuc2Zvcm0gfSBmcm9tICdAYW5ndWxhci9jb3JlJztcclxuXHJcbkBQaXBlKHtcclxuICBuYW1lOiAnYnl0ZXNUb1RleHQnLFxyXG4gIHN0YW5kYWxvbmU6IHRydWUsXHJcbn0pXHJcbmV4cG9ydCBjbGFzcyBCeXRlc1RvVGV4dFBpcGUgaW1wbGVtZW50cyBQaXBlVHJhbnNmb3JtIHtcclxuICBzdGF0aWMgcmVhZG9ubHkgZGVmYXVsdFByZWNpc2lvbiA9IDI7XHJcbiAgc3RhdGljIHJlYWRvbmx5IGRlZmF1bHRVbml0cyA9IFsnQicsICdLQicsICdNQicsICdHQicsICdUQicsICdQQiddO1xyXG5cclxuICB0cmFuc2Zvcm0oXHJcbiAgICBieXRlczogbnVtYmVyLFxyXG4gICAgcHJlY2lzaW9uOiBudW1iZXIgPSBCeXRlc1RvVGV4dFBpcGUuZGVmYXVsdFByZWNpc2lvbixcclxuICAgIHVuaXRzID0gQnl0ZXNUb1RleHRQaXBlLmRlZmF1bHRVbml0c1xyXG4gICk6IHN0cmluZyB7XHJcbiAgICBpZiAodHlwZW9mIHByZWNpc2lvbiAhPT0gJ251bWJlcicgfHwgaXNOYU4ocHJlY2lzaW9uKSB8fCAhaXNGaW5pdGUocHJlY2lzaW9uKSB8fCBwcmVjaXNpb24gPCAwKSB7XHJcbiAgICAgIHByZWNpc2lvbiA9IEJ5dGVzVG9UZXh0UGlwZS5kZWZhdWx0UHJlY2lzaW9uO1xyXG4gICAgfVxyXG5cclxuICAgIGlmICghdW5pdHMgfHwgdW5pdHMubGVuZ3RoIDwgMikge1xyXG4gICAgICB1bml0cyA9IEJ5dGVzVG9UZXh0UGlwZS5kZWZhdWx0VW5pdHM7XHJcbiAgICB9XHJcblxyXG4gICAgY29uc3QgcG93ZXIgPSBNYXRoLm1pbihNYXRoLnJvdW5kKE1hdGgubG9nKGJ5dGVzKSAvIE1hdGgubG9nKDEwMjQpKSwgdW5pdHMubGVuZ3RoIC0gMSk7XHJcbiAgICAvLyBjb25zdCB1bml0SW5kZXggPSB1bml0c1twb3dlcl07XHJcbiAgICBsZXQgdW5pdEluZGV4ID0gcG93ZXI7XHJcbiAgICBsZXQgc2l6ZSA9IGJ5dGVzIC8gTWF0aC5wb3coMTAyNCwgcG93ZXIpOyAvLyBzaXplIGluIG5ldyB1bml0c1xyXG5cclxuICAgIGlmIChzaXplIDwgMC41ICYmIHVuaXRJbmRleCA+IDApIHtcclxuICAgICAgc2l6ZSA9IHNpemUgKiAxMDI0O1xyXG4gICAgICB1bml0SW5kZXggPSB1bml0SW5kZXggLSAxO1xyXG4gICAgfVxyXG5cclxuICAgIGNvbnN0IHJvdW5kTXVsdGlwbGllciA9IHByZWNpc2lvbiA+IDAgPyBNYXRoLnBvdygxMCwgcHJlY2lzaW9uKSA6IDE7XHJcbiAgICBjb25zdCByb3VuZGVkU2l6ZSA9IE1hdGgucm91bmQoc2l6ZSAqIHJvdW5kTXVsdGlwbGllcikgLyByb3VuZE11bHRpcGxpZXI7IC8vIGtlZXAgdXAgdG8gMiBkZWNpbWFsc1xyXG5cclxuICAgIHJldHVybiBgJHtyb3VuZGVkU2l6ZX0gJHt1bml0c1t1bml0SW5kZXhdfWA7XHJcbiAgfVxyXG59XHJcbiJdfQ==