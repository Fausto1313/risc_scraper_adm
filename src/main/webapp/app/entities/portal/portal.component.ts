import { Component, OnInit, OnDestroy, ViewChild } from '@angular/core';
import { HttpErrorResponse, HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks, JhiAlertService } from 'ng-jhipster';
import {MatTableDataSource, MatSort} from '@angular/material';

import { IPortal } from 'app/shared/model/portal.model';
import { Principal } from 'app/core';

import { ITEMS_PER_PAGE } from 'app/shared';
import { PortalService } from './portal.service';

@Component({
    selector: 'jhi-portal',
    templateUrl: './portal.component.html'
})
export class PortalComponent implements OnInit, OnDestroy {
    currentAccount: any;
    portals: IPortal[];
    error: any;
    success: any;
    eventSubscriber: Subscription;
    routeData: any;
    links: any;
    totalItems: any;
    queryCount: any;
    itemsPerPage: any;
    page: any;
    predicate: any;
    previousPage: any;
    reverse: any;
    
    public displayedColumns = ['name', 'domain', 'url', 'path', 'scheduler' , 'details', 'update', 'delete'];
    
    public dataSource = new MatTableDataSource<IPortal>();
    
    @ViewChild(MatSort) sort: MatSort;

    constructor(
        private portalService: PortalService,
        private parseLinks: JhiParseLinks,
        private jhiAlertService: JhiAlertService,
        private principal: Principal,
        private activatedRoute: ActivatedRoute,
        private router: Router,
        private eventManager: JhiEventManager
    ) {
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.routeData = this.activatedRoute.data.subscribe(data => {
            this.page = data.pagingParams.page;
            this.previousPage = data.pagingParams.page;
            this.reverse = data.pagingParams.ascending;
            this.predicate = data.pagingParams.predicate;
        });
    }

    loadAll() {
        this.portalService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,                
            })
            .subscribe(
                (res: HttpResponse<IPortal[]>) => this.paginatePortals(res.body, res.headers),
                (res: HttpErrorResponse) => this.onError(res.message)
            );
            
        this.portalService
            .query({
                page: this.page - 1,
                size: this.itemsPerPage,
                
            })
          .subscribe(res => {
            this.dataSource.data = res.body as IPortal[];
            });
    }

    loadPage(page: number) {
        if (page !== this.previousPage) {
            this.previousPage = page;
            this.transition();
        }
    }

    transition() {
        this.router.navigate(['/portal'], {
            queryParams: {
                page: this.page,
                size: this.itemsPerPage,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        });
        this.loadAll();
    }

    clear() {
        this.page = 0;
        this.router.navigate([
            '/portal',
            {
                page: this.page,
                sort: this.predicate + ',' + (this.reverse ? 'asc' : 'desc')
            }
        ]);
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInPortals();
    }

    ngOnDestroy() {        
    }
    
    public doFilter = (value: string) => {
    this.dataSource.filter = value.trim().toLocaleLowerCase();
  }

    trackId(index: number, item: IPortal) {
        return item.id;
    }

    registerChangeInPortals() {
        this.eventSubscriber = this.eventManager.subscribe('portalListModification', response => this.loadAll());
    }
    
   private paginatePortals(data: IPortal[], headers: HttpHeaders) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = parseInt(headers.get('X-Total-Count'), 10);
        this.queryCount = this.totalItems;
        this.portals = data;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
    
    ngAfterViewInit(): void {
     this.dataSource.sort = this.sort;
    }
}
