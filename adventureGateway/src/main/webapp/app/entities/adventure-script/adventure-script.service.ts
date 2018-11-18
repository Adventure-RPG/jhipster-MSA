import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureScript } from 'app/shared/model/adventure-script.model';

type EntityResponseType = HttpResponse<IAdventureScript>;
type EntityArrayResponseType = HttpResponse<IAdventureScript[]>;

@Injectable({ providedIn: 'root' })
export class AdventureScriptService {
    public resourceUrl = SERVER_API_URL + 'api/adventure-scripts';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-scripts';

    constructor(private http: HttpClient) {}

    create(adventureScript: IAdventureScript): Observable<EntityResponseType> {
        return this.http.post<IAdventureScript>(this.resourceUrl, adventureScript, { observe: 'response' });
    }

    update(adventureScript: IAdventureScript): Observable<EntityResponseType> {
        return this.http.put<IAdventureScript>(this.resourceUrl, adventureScript, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAdventureScript>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureScript[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureScript[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
