import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { REST_API_BASE_URL } from '@itree-commons/src/constants/rest.constants';
import {
  AdvancedSearchParameterStatement,
  NtisAggloMapTableGeom,
  NtisAggloMapTableItem,
  NtisBuildingsMapTableItem,
  NtisCountyMunicipality,
  NtisMapBuildPointDetails,
  NtisMapCentDetails,
  NtisMapClickedPoint,
  NtisMapDisposalDetails,
  NtisMapFacilityDetails,
  NtisMapResearchDetails,
  NtisMapTableResult,
  RecordIdentifier,
} from '@itree-commons/src/lib/model/api/api';
import { AuthUtil } from '@itree/ngx-s2-commons';
import { Feature, VectorTile } from 'ol';
import { GeoJSON } from '../models/geojson';
import { Geometry } from 'ol/geom';
import { LoadFunction } from 'ol/Tile';
import { NtisRoutingConst } from '../constants/ntis-routing.const';
import { Observable, map } from 'rxjs';
import { RoutingConst } from '@itree-commons/src/constants/routing.const';
import TileState from 'ol/TileState';

@Injectable({
  providedIn: 'root',
})
export class NtisMapService {
  readonly internalUrl = `${REST_API_BASE_URL}/ntis-map`;
  readonly publicUrl = `${REST_API_BASE_URL}/ntis-map-pub`;

  constructor(private http: HttpClient) {}

  get url(): string {
    return AuthUtil.isLoggedIn() ? this.internalUrl : this.publicUrl;
  }

  getCountiesWithMunicipalities(): Observable<NtisCountyMunicipality[]> {
    return this.http.get<NtisCountyMunicipality[]>(this.url + '/get-counties-municipalities');
  }

  getMapAggloObjectInfo(id: number): Observable<NtisAggloMapTableItem> {
    const body: RecordIdentifier = { id: `${id}` };
    return this.http.post<NtisAggloMapTableItem>(this.url + '/get-agglo-obj-info', body);
  }

  getMapAggloTable(filters: AdvancedSearchParameterStatement[]): Observable<NtisMapTableResult<NtisAggloMapTableItem>> {
    return this.http.post<NtisMapTableResult<NtisAggloMapTableItem>>(this.url + '/get-agglo-table', filters);
  }

  getMapAggloTableGeoms(filters: AdvancedSearchParameterStatement[]): Observable<Record<number, GeoJSON['geometry']>> {
    return this.http.post<NtisAggloMapTableGeom[]>(this.url + '/get-agglo-table-geoms', filters).pipe(
      map((result) =>
        result.reduce<Record<number, GeoJSON['geometry']>>((accumulator, item) => {
          accumulator[item.id] = JSON.parse(item.geom) as GeoJSON['geometry'];
          return accumulator;
        }, {})
      )
    );
  }

  getBuildingDetails(poId: string | number): Observable<NtisMapBuildPointDetails> {
    return this.http.get<NtisMapBuildPointDetails>(`${this.url}/get-building-details/${poId}`);
  }

  getMapBuildingsTable(
    filters: AdvancedSearchParameterStatement[]
  ): Observable<NtisMapTableResult<NtisBuildingsMapTableItem>> {
    return this.http.post<NtisMapTableResult<NtisBuildingsMapTableItem>>(this.url + '/get-buildings-table', filters);
  }

  getFacilityDetails(wtfId: string | number): Observable<NtisMapFacilityDetails> {
    return this.http.get<NtisMapFacilityDetails>(`${this.url}/get-facility-details/${wtfId}`);
  }

  getMapFacilitiesTable(
    filters: AdvancedSearchParameterStatement[]
  ): Observable<NtisMapTableResult<NtisMapFacilityDetails>> {
    return this.http.post<NtisMapTableResult<NtisMapFacilityDetails>>(this.url + '/get-facilities-table', filters);
  }

  getDischargeDetails(wtfId: string | number): Observable<NtisMapFacilityDetails> {
    return this.http.get<NtisMapFacilityDetails>(`${this.url}/get-discharge-details/${wtfId}`);
  }

  getMapDischargesTable(
    filters: AdvancedSearchParameterStatement[]
  ): Observable<NtisMapTableResult<NtisMapFacilityDetails>> {
    return this.http.post<NtisMapTableResult<NtisMapFacilityDetails>>(this.url + '/get-discharges-table', filters);
  }

  getMapCentTable(filters: AdvancedSearchParameterStatement[]): Observable<NtisMapTableResult<NtisMapCentDetails>> {
    return this.http.post<NtisMapTableResult<NtisMapCentDetails>>(this.url + '/get-cent-table', filters);
  }

  getCentDetails(wtfId: string | number): Observable<NtisMapCentDetails> {
    return this.http.get<NtisMapCentDetails>(`${this.url}/get-cent-details/${wtfId}`).pipe(
      map((result) => {
        if (result.ntrNumber?.startsWith('[') && result.ntrNumber.endsWith(']')) {
          result.ntrNumber = (JSON.parse(result.ntrNumber) as string[])?.join(', ');
        }
        return result;
      })
    );
  }

  getMapResearchTable(
    filters: AdvancedSearchParameterStatement[]
  ): Observable<NtisMapTableResult<NtisMapResearchDetails>> {
    return this.http.post<NtisMapTableResult<NtisMapResearchDetails>>(this.url + '/get-research-table', filters).pipe(
      map((result) => {
        result.items.forEach((row) => {
          if (row.wtfId) {
            row.link = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.MAP}/${NtisRoutingConst.INSPECTOR_RESEARCH_ORDERS_LIST}/${row.wtfId}`;
          }
        });
        return result;
      })
    );
  }

  getResearchDetails(id: string | number): Observable<NtisMapResearchDetails> {
    return this.http.get<NtisMapResearchDetails>(`${this.url}/get-research-details/${id}`).pipe(
      map((result) => {
        if (result.wtfId) {
          result.link = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.MAP}/${NtisRoutingConst.INSPECTOR_RESEARCH_ORDERS_LIST}/${result.wtfId}`;
        }
        return result;
      })
    );
  }

  getMapDisposalTable(
    filters: AdvancedSearchParameterStatement[]
  ): Observable<NtisMapTableResult<NtisMapDisposalDetails>> {
    return this.http.post<NtisMapTableResult<NtisMapDisposalDetails>>(this.url + '/get-disposal-table', filters).pipe(
      map((result) => {
        result.items.forEach((row) => {
          if (row.wtfId) {
            row.link = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.MAP}/${NtisRoutingConst.INSPECTOR_DISPOSAL_ORDERS_LIST}/${row.wtfId}`;
          }
        });
        return result;
      })
    );
  }

  getDisposalDetails(id: string | number): Observable<NtisMapDisposalDetails> {
    return this.http.get<NtisMapDisposalDetails>(`${this.url}/get-disposal-details/${id}`).pipe(
      map((result) => {
        if (result.wtfId) {
          result.link = `/${RoutingConst.INTERNAL}/${NtisRoutingConst.MAP}/${NtisRoutingConst.INSPECTOR_DISPOSAL_ORDERS_LIST}/${result.wtfId}`;
        }
        return result;
      })
    );
  }

  getNamesOfClickedPoints(points: NtisMapClickedPoint[]): Observable<NtisMapClickedPoint[]> {
    return this.http.post<NtisMapClickedPoint[]>(`${this.url}/get-clicked-points-names`, points);
  }

  loadVectorTileWithAuth: LoadFunction = (tile, url) => {
    const vectorTile = tile as VectorTile;
    vectorTile.setLoader((extent, _resolution, projection) => {
      this.http
        .get(url, {
          headers: new HttpHeaders({ Authorization: `Bearer ${AuthUtil.getJWTFromSession()}` }),
          responseType: 'arraybuffer',
        })
        .subscribe({
          next: (result) => {
            const format = vectorTile.getFormat();
            vectorTile.setFeatures(
              format.readFeatures(result, {
                extent,
                featureProjection: projection,
              }) as Feature<Geometry>[]
            );
            format.readProjection(result);
          },
          error: () => {
            tile.setState(TileState.ERROR);
          },
        });
    });
  };
}
