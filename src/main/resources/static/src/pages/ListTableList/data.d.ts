export interface TableListItem {
  id: number;
  disabled?: boolean;
  name: string;
  account: string;
  passwd: string;
  path: string;
  type: string;
  ip: string;
  port: number;
}

export interface TableListPagination {
  total: number;
  pageSize: number;
  current: number;
}

export interface TableListData {
  list: TableListItem[];
  pagination: Partial<TableListPagination>;
}

export interface TableListParams {
  status?: string;
  name?: string;
  pageSize?: number;
  currentPage?: number;
  filter?: { [key: string]: any[] };
  sorter?: { [key: string]: any };
}
