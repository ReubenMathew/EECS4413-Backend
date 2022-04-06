import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  stages: [
    { duration: '2m', target: 20 }, // below normal load
    { duration: '5m', target: 40 },
    { duration: '5m', target: 80 }, // beyond the breaking point
    { duration: '2m', target: 100 },
    { duration: '1m', target: 0 }, // scale down. Recovery stage.
  ],
};

export default function () {
  const BASE_URL = 'https://shopcart-backend.fly.dev'; // make sure this is not production

  const responses = http.batch([
    ['GET', `${BASE_URL}/api/products`, null, { tags: { name: 'GetProducts' } }],
    ['GET', `${BASE_URL}/api/analytics/monthly/items`, null, { tags: { name: 'GetProducts' } }],
    ['GET', `${BASE_URL}/api/products`, null, { tags: { name: 'GetProducts' } }],
  ]);

  sleep(1);
}

