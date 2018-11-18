import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
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
        private jhiAlertService: JhiAlertService,
        private adventureItemService: AdventureItemService,
        private adventureAttributesService: AdventureAttributesService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adventureItem }) => {
            this.adventureItem = adventureItem;
        });
        this.adventureAttributesService.query({ filter: 'adventureitem-is-null' }).subscribe(
            (res: HttpResponse<IAdventureAttributes[]>) => {
                if (!this.adventureItem.adventureAttributesId) {
                    this.adventureattributes = res.body;
                } else {
                    this.adventureAttributesService.find(this.adventureItem.adventureAttributesId).subscribe(
                        (subRes: HttpResponse<IAdventureAttributes>) => {
                            this.adventureattributes = [subRes.body].concat(res.body);
                        },
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

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdventureItem>>) {
        result.subscribe((res: HttpResponse<IAdventureItem>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackAdventureAttributesById(index: number, item: IAdventureAttributes) {
        return item.id;
    }
}
