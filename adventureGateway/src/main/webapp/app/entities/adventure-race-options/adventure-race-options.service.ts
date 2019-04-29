import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureRaceOptions } from 'app/shared/model/adventure-race-options.model';

type EntityResponseType = HttpResponse<IAdventureRaceOptions>;
type EntityArrayResponseType = HttpResponse<IAdventureRaceOptions[]>;

@Injectable({ providedIn: 'root' })
export class AdventureRaceOptionsService {
    public resourceUrl = SERVER_API_URL + 'api/adventure-race-options';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-race-options';

    constructor(protected http: HttpClient) {}

    create(adventureRaceOptions: IAdventureRaceOptions): Observable<EntityResponseType> {
        return this.http.post<IAdventureRaceOptions>(this.resourceUrl, adventureRaceOptions, { observe: 'response' });
    }

    update(adventureRaceOptions: IAdventureRaceOptions): Observable<EntityResponseType> {
        return this.http.put<IAdventureRaceOptions>(this.resourceUrl, adventureRaceOptions, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IAdventureRaceOptions>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureRaceOptions[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureRaceOptions[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
