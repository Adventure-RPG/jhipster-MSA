import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureFraction } from 'app/shared/model/adventure-fraction.model';

type EntityResponseType = HttpResponse<IAdventureFraction>;
type EntityArrayResponseType = HttpResponse<IAdventureFraction[]>;

@Injectable({ providedIn: 'root' })
export class AdventureFractionService {
    public resourceUrl = SERVER_API_URL + 'api/adventure-fractions';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-fractions';

    constructor(protected http: HttpClient) {}

    create(adventureFraction: IAdventureFraction): Observable<EntityResponseType> {
        return this.http.post<IAdventureFraction>(this.resourceUrl, adventureFraction, { observe: 'response' });
    }

    update(adventureFraction: IAdventureFraction): Observable<EntityResponseType> {
        return this.http.put<IAdventureFraction>(this.resourceUrl, adventureFraction, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IAdventureFraction>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureFraction[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureFraction[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
