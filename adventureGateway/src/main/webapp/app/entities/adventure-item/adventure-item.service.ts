import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureItem } from 'app/shared/model/adventure-item.model';

type EntityResponseType = HttpResponse<IAdventureItem>;
type EntityArrayResponseType = HttpResponse<IAdventureItem[]>;

@Injectable({ providedIn: 'root' })
export class AdventureItemService {
    public resourceUrl = SERVER_API_URL + 'api/adventure-items';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-items';

    constructor(protected http: HttpClient) {}

    create(adventureItem: IAdventureItem): Observable<EntityResponseType> {
        return this.http.post<IAdventureItem>(this.resourceUrl, adventureItem, { observe: 'response' });
    }

    update(adventureItem: IAdventureItem): Observable<EntityResponseType> {
        return this.http.put<IAdventureItem>(this.resourceUrl, adventureItem, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IAdventureItem>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureItem[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureItem[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
