import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureAccountCharacter } from 'app/shared/model/adventure-account-character.model';

type EntityResponseType = HttpResponse<IAdventureAccountCharacter>;
type EntityArrayResponseType = HttpResponse<IAdventureAccountCharacter[]>;

@Injectable({ providedIn: 'root' })
export class AdventureAccountCharacterService {
  public resourceUrl = SERVER_API_URL + 'api/adventure-account-characters';
  public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-account-characters';

  constructor(protected http: HttpClient) {}

  create(adventureAccountCharacter: IAdventureAccountCharacter): Observable<EntityResponseType> {
    return this.http.post<IAdventureAccountCharacter>(this.resourceUrl, adventureAccountCharacter, { observe: 'response' });
  }

  update(adventureAccountCharacter: IAdventureAccountCharacter): Observable<EntityResponseType> {
    return this.http.put<IAdventureAccountCharacter>(this.resourceUrl, adventureAccountCharacter, { observe: 'response' });
  }

  find(id: string): Observable<EntityResponseType> {
    return this.http.get<IAdventureAccountCharacter>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureAccountCharacter[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: string): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  search(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAdventureAccountCharacter[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
  }
}
