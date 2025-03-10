import { NextRequest, NextResponse } from 'next/server';

export async function GET(request: NextRequest) {
  try {
    // Extract path from URL instead of params
    const url = new URL(request.url);
    const pathSegments = url.pathname.split('/api/')[1]; // Get everything after /api/
    
    const apiUrl = `http://localhost:8080/api/${pathSegments}`;
    
    const response = await fetch(apiUrl, {
      headers: {
        'Content-Type': 'application/json',
      },
      cache: 'no-store',
    });
    
    const data = await response.json().catch(() => null);
    
    return NextResponse.json(data, {
      status: response.status,
      statusText: response.statusText,
    });
  } catch (error) {
    console.error('Proxy error:', error);
    return NextResponse.json({ error: 'Internal Server Error' }, { status: 500 });
  }
}

export async function POST(request: NextRequest) {
  try {
    // Extract path from URL instead of params
    const url = new URL(request.url);
    const pathSegments = url.pathname.split('/api/')[1]; // Get everything after /api/
    
    const apiUrl = `http://localhost:8080/api/${pathSegments}`;
    
    const body = await request.json().catch(() => ({}));
    
    const response = await fetch(apiUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
    });
    
    const data = await response.json().catch(() => null);
    
    return NextResponse.json(data, {
      status: response.status,
      statusText: response.statusText,
    });
  } catch (error) {
    console.error('Proxy error:', error);
    return NextResponse.json({ error: 'Internal Server Error' }, { status: 500 });
  }
}

// Add other HTTP methods as needed
export async function PUT(request: NextRequest) {
  try {
    // Extract path from URL instead of params
    const url = new URL(request.url);
    const pathSegments = url.pathname.split('/api/')[1]; // Get everything after /api/
    
    const apiUrl = `http://localhost:8080/api/${pathSegments}`;
    
    const body = await request.json().catch(() => ({}));
    
    const response = await fetch(apiUrl, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(body),
    });
    
    const data = await response.json().catch(() => null);
    
    return NextResponse.json(data, {
      status: response.status,
      statusText: response.statusText,
    });
  } catch (error) {
    console.error('Proxy error:', error);
    return NextResponse.json({ error: 'Internal Server Error' }, { status: 500 });
  }
}

export async function DELETE(request: NextRequest) {
  try {
    // Extract path from URL instead of params
    const url = new URL(request.url);
    const pathSegments = url.pathname.split('/api/')[1]; // Get everything after /api/
    
    const apiUrl = `http://localhost:8080/api/${pathSegments}`;
    
    const response = await fetch(apiUrl, {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
      },
    });
    
    const data = await response.json().catch(() => null);
    
    return NextResponse.json(data, {
      status: response.status,
      statusText: response.statusText,
    });
  } catch (error) {
    console.error('Proxy error:', error);
    return NextResponse.json({ error: 'Internal Server Error' }, { status: 500 });
  }
} 