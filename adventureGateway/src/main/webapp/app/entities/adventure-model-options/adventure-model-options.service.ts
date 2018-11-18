import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureModelOptions } from 'app/shared/model/adventure-model-options.model';

type EntityResponseType = HttpResponse<IAdventureModelOptions>;
type EntityArrayResponseType = HttpResponse<IAdventureModelOptions[]>;

@Injectable({ providedIn: 'root' })
export class AdventureModelOptionsService {
    public resourceUrl = SERVER_API_URL + 'api/adventure-model-options';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-model-options';

    constructor(private http: HttpClient) {}

    create(adventureModelOptions: IAdventureModelOptions): Observable<EntityResponseType> {
        return this.http.post<IAdventureModelOptions>(this.resourceUrl, adventureModelOptions, { observe: 'response' });
    }

    update(adventureModelOptions: IAdventureModelOptions): Observable<EntityResponseType> {
        return this.http.put<IAdventureModelOptions>(this.resourceUrl, adventureModelOptions, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAdventureModelOptions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureModelOptions[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureModelOptions[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
