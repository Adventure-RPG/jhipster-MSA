import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureCategoryType } from 'app/shared/model/adventure-category-type.model';

type EntityResponseType = HttpResponse<IAdventureCategoryType>;
type EntityArrayResponseType = HttpResponse<IAdventureCategoryType[]>;

@Injectable({ providedIn: 'root' })
export class AdventureCategoryTypeService {
  public resourceUrl = SERVER_API_URL + 'api/adventure-category-types';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-category-types';

  constructor(protected http: HttpClient) {}

  create(adventureCategoryType: IAdventureCategoryType): Observable<EntityResponseType> {
    return this.http.post<IAdventureCategoryType>(this.resourceUrl, adventureCategoryType, { observe: 'response' });
  }

  update(adventureCategoryType: IAdventureCategoryType): Observable<EntityResponseType> {
    return this.http.put<IAdventureCategoryType>(this.resourceUrl, adventureCategoryType, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAdventureCategoryType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureCategoryType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureCategoryType[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
