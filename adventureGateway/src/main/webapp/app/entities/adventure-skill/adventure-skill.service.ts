import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdventureSkill } from 'app/shared/model/adventure-skill.model';

type EntityResponseType = HttpResponse<IAdventureSkill>;
type EntityArrayResponseType = HttpResponse<IAdventureSkill[]>;

@Injectable({ providedIn: 'root' })
export class AdventureSkillService {
    public resourceUrl = SERVER_API_URL + 'api/adventure-skills';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/adventure-skills';

    constructor(protected http: HttpClient) {}

    create(adventureSkill: IAdventureSkill): Observable<EntityResponseType> {
        return this.http.post<IAdventureSkill>(this.resourceUrl, adventureSkill, { observe: 'response' });
    }

    update(adventureSkill: IAdventureSkill): Observable<EntityResponseType> {
        return this.http.put<IAdventureSkill>(this.resourceUrl, adventureSkill, { observe: 'response' });
    }

    find(id: string): Observable<EntityResponseType> {
        return this.http.get<IAdventureSkill>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureSkill[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: string): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdventureSkill[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
