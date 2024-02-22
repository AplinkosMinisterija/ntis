import { AfterViewInit, Component, ElementRef, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { Chart, registerables } from 'chart.js';
import ChartDataLabels from 'chartjs-plugin-datalabels';
Chart.register(...registerables, ChartDataLabels);

@Component({
  selector: 'ntis-bar-chart',
  templateUrl: './bar-chart.component.html',
  styleUrls: ['./bar-chart.component.scss'],
})
export class BarChartComponent implements AfterViewInit {
  @Input() labels: string[];
  @Input() data: number[];
  @Input() periodLabelTranslationsReference: string;
  @Input() colors: string[] = ['rgb(65, 94, 114)'];
  @Input() placeHolder: string;
  @Input() hasPeriodSelection: boolean = true;

  @ViewChild('barChartRef') barChartRef: ElementRef<HTMLCanvasElement>;
  chart: Chart;
  selectedPeriod: string = '';

  @Output() setNewPeriod = new EventEmitter<string>();

  ngAfterViewInit(): void {
    this.renderChart();
  }

  renderChart(): void {
    if (this.data != null) {
      this.chart = new Chart(this.barChartRef.nativeElement, {
        type: 'bar',
        data: {
          labels: this.labels,
          datasets: [
            {
              backgroundColor: this.colors,
              data: this.data,
            },
          ],
        },
        options: {
          layout: {
            padding: {
              top: 25,
            },
          },
          scales: {
            x: {
              grid: {
                display: false,
              },
            },
            y: {
              suggestedMax: Math.max(...this.data) > 10 ? Math.max(...this.data) + 10 : 10,
            },
          },
          responsive: true,
          maintainAspectRatio: false,
          plugins: {
            datalabels: {
              color: 'rgb(55, 58, 60)',
              anchor: 'end',
              align: 'top',
              font: {
                weight: 'bold',
                size: 14,
              },
            },
            legend: {
              display: false,
            },
          },
        },
      });
    }
  }
}
