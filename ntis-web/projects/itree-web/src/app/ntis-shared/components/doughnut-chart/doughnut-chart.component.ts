import { AfterViewInit, Component, ElementRef, Input, OnChanges, SimpleChanges, ViewChild } from '@angular/core';
import { Chart, registerables } from 'chart.js';
Chart.register(...registerables);

@Component({
  selector: 'ntis-doughnut-chart',
  templateUrl: './doughnut-chart.component.html',
  styleUrls: ['./doughnut-chart.component.scss'],
})
export class DoughnutChartComponent implements AfterViewInit, OnChanges {
  @Input() labels: string[];
  @Input() data: number[];
  @Input() chartName: string;
  @Input() showLegend: boolean = false;
  @Input() colors: string[];

  dataForLabels: number[] = [];

  @ViewChild('chartRef') chartRef: ElementRef<HTMLCanvasElement>;
  @ViewChild('legendRef') legendRef: ElementRef<HTMLCanvasElement>;
  chart: Chart<'doughnut', number[], string>;
  legend: Chart<'doughnut', number[], string>;

  ngOnChanges(changes: SimpleChanges): void {
    if (changes.data && this.data instanceof Array) {
      while (this.dataForLabels.length < this.data.length) {
        this.dataForLabels.push(0);
      }
    }
    if (!changes.colors) {
      if (this.data.length <= 3) {
        this.colors = ['rgb(65, 94, 114)', 'rgb(39, 174, 97)', 'rgb(243, 92, 56)'];
      } else {
        this.colors = [
          'rgb(93, 118, 136)',
          'rgb(65, 94, 114)',
          'rgb(87, 205, 128)',
          'rgb(39, 174, 97)',
          'rgb(249, 132, 103)',
          'rgb(243, 92, 56)',
        ];
      }
    }
  }

  ngAfterViewInit(): void {
    this.renderChart();
  }

  renderChart(): void {
    if (this.data !== null) {
      this.chart = new Chart(this.chartRef.nativeElement, {
        type: 'doughnut',
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
          responsive: true,
          plugins: {
            datalabels: {
              display: true,
              color: 'rgb(255, 255, 255)',
              font: {
                weight: 'bold',
                size: 14,
              },
            },
            legend: {
              display: false,
            },
            title: {
              text: this.chartName,
              display: true,
              color: 'rgb(65, 94, 114)',
              font: {
                size: 16,
                weight: 'normal',
              },
            },
          },
        },
      });

      if (this.showLegend) {
        this.legend = new Chart(this.legendRef.nativeElement, {
          type: 'doughnut',
          data: {
            labels: this.labels,
            datasets: [
              {
                backgroundColor: this.colors,
                data: this.dataForLabels,
              },
            ],
          },
          options: {
            plugins: {
              datalabels: {
                display: false,
              },
              legend: {
                display: true,
                maxWidth: 250,
                position: 'left',
                labels: {
                  color: 'rgb(65, 94, 114)',
                  boxWidth: 20,
                  boxHeight: 20,
                  font: {
                    size: 14,
                  },
                },
              },
            },
          },
        });
      }
    }
  }
}
