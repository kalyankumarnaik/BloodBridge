import React, { useEffect, useRef } from 'react';
import L from 'leaflet';
import { BloodRequest, DonorSearchResult } from '../types';

interface MapViewerProps {
  center?: [number, number];
  zoom?: number;
  requests?: BloodRequest[];
  donors?: DonorSearchResult[];
  height?: string;
  className?: string;
  onSelectRequest?: (req: BloodRequest) => void;
  onSelectDonor?: (donor: DonorSearchResult) => void;
}

export const MapViewer: React.FC<MapViewerProps> = ({
  center = [12.9716, 77.5946], // Default Bangalore / India
  zoom = 11,
  requests = [],
  donors = [],
  height = '450px',
  className = '',
  onSelectRequest,
  onSelectDonor,
}) => {
  const mapContainerRef = useRef<HTMLDivElement | null>(null);
  const mapInstanceRef = useRef<L.Map | null>(null);
  const layerGroupRef = useRef<L.LayerGroup | null>(null);

  useEffect(() => {
    if (!mapContainerRef.current) return;

    if (!mapInstanceRef.current) {
      const map = L.map(mapContainerRef.current, {
        center,
        zoom,
        zoomControl: true,
        scrollWheelZoom: false,
      });

      L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
        maxZoom: 18,
      }).addTo(map);

      const layerGroup = L.layerGroup().addTo(map);
      layerGroupRef.current = layerGroup;
      mapInstanceRef.current = map;
    } else {
      mapInstanceRef.current.setView(center, zoom);
    }

    return () => {
      if (mapInstanceRef.current) {
        mapInstanceRef.current.remove();
        mapInstanceRef.current = null;
      }
    };
  }, []);

  // Update markers when requests or donors change
  useEffect(() => {
    if (!mapInstanceRef.current || !layerGroupRef.current) return;

    layerGroupRef.current.clearLayers();

    // Custom request icon (Red pin)
    const requestIcon = L.divIcon({
      className: 'custom-map-marker-request',
      html: `<div style="background-color:#e11d48; width:28px; height:28px; border-radius:50%; border:3px solid #ffffff; box-shadow:0 3px 8px rgba(0,0,0,0.4); display:flex; align-items:center; justify-content:center; color:#fff; font-size:10px; font-weight:bold;">🩸</div>`,
      iconSize: [28, 28],
      iconAnchor: [14, 14],
      popupAnchor: [0, -14],
    });

    // Custom donor privacy circle/icon (Emerald pin)
    const donorIcon = L.divIcon({
      className: 'custom-map-marker-donor',
      html: `<div style="background-color:#059669; width:24px; height:24px; border-radius:50%; border:2px solid #ffffff; box-shadow:0 2px 6px rgba(0,0,0,0.3); display:flex; align-items:center; justify-content:center; color:#fff; font-size:11px; font-weight:bold;">✓</div>`,
      iconSize: [24, 24],
      iconAnchor: [12, 12],
      popupAnchor: [0, -12],
    });

    // Plot blood requests
    requests.forEach((req) => {
      // If no exact coords, fallback to center with small deterministic jitter
      const lat = req.hospitalLatitude || center[0] + (Math.random() - 0.5) * 0.05;
      const lon = req.hospitalLongitude || center[1] + (Math.random() - 0.5) * 0.05;

      const marker = L.marker([lat, lon], { icon: requestIcon });
      const popupHtml = `
        <div style="font-family:inherit; min-width:180px; padding:4px;">
          <div style="font-size:10px; font-weight:700; color:#e11d48; text-transform:uppercase;">${req.priority} PRIORITY</div>
          <div style="font-size:13px; font-weight:700; margin:2px 0;">Blood Need: ${req.bloodGroup}</div>
          <div style="font-size:11px; color:#475569;">Hospital: ${req.hospitalName}</div>
          <div style="font-size:11px; color:#64748b;">${req.unitsRequired} Units • ${req.city}</div>
        </div>
      `;
      marker.bindPopup(popupHtml);
      if (onSelectRequest) {
        marker.on('click', () => onSelectRequest(req));
      }
      layerGroupRef.current?.addLayer(marker);
    });

    // Plot donors (Privacy note: NEVER exact coordinates, approximate radius only)
    donors.forEach((donor, idx) => {
      // Approximate jitter for privacy
      const jitterLat = (Math.sin(idx + 1) * 0.03);
      const jitterLon = (Math.cos(idx + 1) * 0.03);
      const lat = center[0] + jitterLat;
      const lon = center[1] + jitterLon;

      const marker = L.marker([lat, lon], { icon: donorIcon });
      const popupHtml = `
        <div style="font-family:inherit; min-width:160px; padding:4px;">
          <div style="font-size:10px; font-weight:700; color:#059669;">VOLUNTARY DONOR</div>
          <div style="font-size:13px; font-weight:700; margin:2px 0;">${donor.displayName} (${donor.bloodGroup})</div>
          <div style="font-size:11px; color:#475569;">${donor.city} • Band: ${donor.distanceBand}</div>
          <div style="font-size:9px; color:#94a3b8; margin-top:3px;">Approx. area only (DPDP Privacy Safe)</div>
        </div>
      `;
      marker.bindPopup(popupHtml);
      if (onSelectDonor) {
        marker.on('click', () => onSelectDonor(donor));
      }
      layerGroupRef.current?.addLayer(marker);

      // Draw privacy circle
      const circle = L.circle([lat, lon], {
        radius: 1500, // 1.5 km privacy radius
        color: '#10b981',
        fillColor: '#10b981',
        fillOpacity: 0.1,
        weight: 1,
      });
      layerGroupRef.current?.addLayer(circle);
    });
  }, [requests, donors, center, onSelectRequest, onSelectDonor]);

  return (
    <div
      ref={mapContainerRef}
      style={{ height }}
      className={`w-full rounded-2xl overflow-hidden border border-slate-200 dark:border-slate-700/80 shadow-sm z-0 relative ${className}`}
    />
  );
};
