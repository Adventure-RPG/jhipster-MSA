import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAdventureItem } from 'app/shared/model/adventure-item.model';
import { AdventureItemService } from './adventure-item.service';
import { IAdventureAttributes } from 'app/shared/model/adventure-attributes.model';
import { AdventureAttributesService } from 'app/entities/adventure-attributes';

@Component({
    selector: 'jhi-adventure-item-update',
    templateUrl: './adventure-item-update.component.html'
})
export class AdventureItemUpdateComponent implements OnInit {
    adventureItem: IAdventureItem;
    isSaving: boolean;

    adventureattributes: IAdventureAttributes[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected adventureItemService: AdventureItemService,
        protected adventureAttributesService: AdventureAttributesService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureItem }) => {
            this.adventureItem = adventureItem;
        });
        this.adventureAttributesService
            .query({ filter: 'adventureitem-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IAdventureAttributes[]>) => mayBeOk.ok),
                map((response: HttpResponse<IAdventureAttributes[]>) => response.body)
            )
            .subscribe(
                (res: IAdventureAttributes[]) => {
                    if (!this.adventureItem.adventureAttributesId) {
                        this.adventureattributes = res;
                    } else {
                        this.adventureAttributesService
                            .find(this.adventureItem.adventureAttributesId)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IAdventureAttributes>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IAdventureAttributes>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IAdventureAttributes) => (this.adventureattributes = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adventureItem.id !== undefined) {
            this.subscribeToSaveResponse(this.adventureItemService.update(this.adventureItem));
        } else {
            this.subscribeToSaveResponse(this.adventureItemService.create(this.adventureItem));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureItem>>) {
        result.subscribe((res: HttpResponse<IAdventureItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAdventureAttributesById(index: number, item: IAdventureAttributes) {
        return item.id;
    }
}
