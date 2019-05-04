import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureInventoryChar } from 'app/shared/model/adventure-inventory-char.model';

type EntityResponseType = HttpResponse<IAdventureInventoryChar>;
type EntityArrayResponseType = HttpResponse<IAdventureInventoryChar[]>;

@Injectable({ providedIn: 'root' })
export class AdventureInventoryCharService {
  public resourceUrl = SERVER_API_URL + 'api/adventure-inventory-chars';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-inventory-chars';

  constructor(protected http: HttpClient) {}

  create(adventureInventoryChar: IAdventureInventoryChar): Observable<EntityResponseType> {
    return this.http.post<IAdventureInventoryChar>(this.resourceUrl, adventureInventoryChar, { observe: 'response' });
  }

  update(adventureInventoryChar: IAdventureInventoryChar): Observable<EntityResponseType> {
    return this.http.put<IAdventureInventoryChar>(this.resourceUrl, adventureInventoryChar, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAdventureInventoryChar>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureInventoryChar[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureInventoryChar[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
