import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureModel } from 'app/shared/model/adventure-model.model';

type EntityResponseType = HttpResponse<IAdventureModel>;
type EntityArrayResponseType = HttpResponse<IAdventureModel[]>;

@Injectable({ providedIn: 'root' })
export class AdventureModelService {
  public resourceUrl = SERVER_API_URL + 'api/adventure-models';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-models';

  constructor(protected http: HttpClient) {}

  create(adventureModel: IAdventureModel): Observable<EntityResponseType> {
    return this.http.post<IAdventureModel>(this.resourceUrl, adventureModel, { observe: 'response' });
  }

  update(adventureModel: IAdventureModel): Observable<EntityResponseType> {
    return this.http.put<IAdventureModel>(this.resourceUrl, adventureModel, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAdventureModel>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureModel[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureModel[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
