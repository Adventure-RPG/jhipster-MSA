import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureCharacteristic } from 'app/shared/model/adventure-characteristic.model';

type EntityResponseType = HttpResponse<IAdventureCharacteristic>;
type EntityArrayResponseType = HttpResponse<IAdventureCharacteristic[]>;

@Injectable({ providedIn: 'root' })
export class AdventureCharacteristicService {
  public resourceUrl = SERVER_API_URL + 'api/adventure-characteristics';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-characteristics';

  constructor(protected http: HttpClient) {}

  create(adventureCharacteristic: IAdventureCharacteristic): Observable<EntityResponseType> {
    return this.http.post<IAdventureCharacteristic>(this.resourceUrl, adventureCharacteristic, { observe: 'response' });
  }

  update(adventureCharacteristic: IAdventureCharacteristic): Observable<EntityResponseType> {
    return this.http.put<IAdventureCharacteristic>(this.resourceUrl, adventureCharacteristic, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAdventureCharacteristic>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureCharacteristic[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureCharacteristic[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
