import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureAttributes } from 'app/shared/model/adventure-attributes.model';

type EntityResponseType = HttpResponse<IAdventureAttributes>;
type EntityArrayResponseType = HttpResponse<IAdventureAttributes[]>;

@Injectable({ providedIn: 'root' })
export class AdventureAttributesService {
  public resourceUrl = SERVER_API_URL + 'api/adventure-attributes';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-attributes';

  constructor(protected http: HttpClient) {}

  create(adventureAttributes: IAdventureAttributes): Observable<EntityResponseType> {
    return this.http.post<IAdventureAttributes>(this.resourceUrl, adventureAttributes, { observe: 'response' });
  }

  update(adventureAttributes: IAdventureAttributes): Observable<EntityResponseType> {
    return this.http.put<IAdventureAttributes>(this.resourceUrl, adventureAttributes, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAdventureAttributes>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureAttributes[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureAttributes[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
