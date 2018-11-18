import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureRace } from 'app/shared/model/adventure-race.model';

type EntityResponseType = HttpResponse<IAdventureRace>;
type EntityArrayResponseType = HttpResponse<IAdventureRace[]>;

@Injectable({ providedIn: 'root' })
export class AdventureRaceService {
    public resourceUrl = SERVER_API_URL + 'api/adventure-races';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-races';

    constructor(private http: HttpClient) {}

    create(adventureRace: IAdventureRace): Observable<EntityResponseType> {
        return this.http.post<IAdventureRace>(this.resourceUrl, adventureRace, { observe: 'response' });
    }

    update(adventureRace: IAdventureRace): Observable<EntityResponseType> {
        return this.http.put<IAdventureRace>(this.resourceUrl, adventureRace, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAdventureRace>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureRace[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureRace[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
