import { HttpBackend, HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MsearchMultisearchBody, SearchResponseBody } from '@elastic/elasticsearch/lib/api/types';
import { Observable, catchError, map } from 'rxjs';
import { MapLocationSearchResult } from '../types/map-location-search-result';
import { LoaderService } from '@itree/ngx-s2-commons';

@Injectable({
  providedIn: 'root',
})
export class MapService {
  httpBackend: HttpClient;

  constructor(handler: HttpBackend, private loaderService: LoaderService) {
    this.httpBackend = new HttpClient(handler);
  }

  getCapabilitiesResponse(url: string): Observable<string> {
    return this.httpBackend.get(url, { responseType: 'text' });
  }

  getLocationResults(
    text: string,
    filters?: Record<string, string[]>,
    filterWeights?: Record<string, number>
  ): Observable<MapLocationSearchResult[]> {
    const url = 'https://www.geoportal.lt/mapproxy/elasticsearch_gvdr';

    const body: MsearchMultisearchBody = {
      query: {
        function_score: {
          query: {
            bool: {
              must: {
                multi_match: {
                  query: text,
                  type: 'most_fields',
                  fields: [
                    'name^5',
                    'name.folded^5',
                    'name.shingle^5',
                    'name.trigram^3',
                    'name.edge^5',
                    'namegenitive',
                    'namegenitive.folded',
                    'namegenitive.shingle',
                    'namegenitive.trigram',
                    'namegenitive.edge',
                  ],
                  slop: 5,
                },
              },
              must_not: [],
              filter: [] as unknown[],
            },
          },
          functions: [],
        },
      },
      sort: [
        '_score',
        {
          gyvsk: 'desc',
        },
        'name',
      ],
      from: 0,
      size: 20,
    };

    const filterKeys = filters ? Object.keys(filters) : [];
    if (filterKeys.length) {
      // @ts-ignore
      body.query.function_score.functions = filterKeys.map((filterKey) => ({
        filter: { term: { [filterKey]: filters[filterKey] } },
        weight: filterWeights?.[filterKey] || 1,
      }));
    }
    const headers = new HttpHeaders({
      'Content-Type': 'text/plain; charset=UTF-8',
    });

    this.loaderService.show(undefined);
    return this.httpBackend.post<SearchResponseBody<Record<string, string>>>(url, body, { headers }).pipe(
      map((response) => {
        this.loaderService.hide();
        if (response?.hits?.hits?.length) {
          const responseItems = response.hits.hits;
          return responseItems
            .filter((searchHit) => searchHit._source)
            .map(({ _source: item }) => {
              const subtypeCapitalized =
                item.subtype && `${item.subtype.charAt(0).toUpperCase()}${item.subtype.substring(1) || ''}`;
              const resultItem: MapLocationSearchResult = {
                coordinates: undefined,
                extent: undefined,
                name: item.name,
                type: subtypeCapitalized,
                description:
                  item.municipality && item.municipality.toLowerCase() !== 'null'
                    ? `${subtypeCapitalized ? `${subtypeCapitalized} - ` : ''}${item.municipality}`
                    : undefined,
              };
              if (item.LOCATIONX && item.LOCATIONY) {
                resultItem.coordinates = [parseFloat(item.LOCATIONX), parseFloat(item.LOCATIONY)];
              }
              if (item.MINX && item.MINY && item.MAXX && item.MAXY) {
                resultItem.extent = [
                  parseFloat(item.MINX),
                  parseFloat(item.MINY),
                  parseFloat(item.MAXX),
                  parseFloat(item.MAXY),
                ];
              }
              return resultItem;
            });
        } else {
          return [];
        }
      }),
      catchError((err) => {
        this.loaderService.hide();
        throw err;
      })
    );
  }
}
